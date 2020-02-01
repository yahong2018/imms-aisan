package com.zhxh.si.imms.kocheer.session;

import com.zhxh.imms.mfc.domain.*;
import com.zhxh.imms.mfc.logic.*;
import com.zhxh.imms.org.domain.Workshop;
import com.zhxh.imms.org.domain.Workstation;
import com.zhxh.si.imms.kocheer.command.Command_28;
import com.zhxh.si.imms.kocheer.domain.WorkstationSession;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/*
通过刷卡进行报工、移库、绑卡、外发、回厂操作
     所有看板：报工、移库
     外发看板：绑卡、外发
     外发前加工车间报工：必须先绑定一个外发看板
     外发车间：外发看板在"外发工位"上打卡，需要将外发看板所绑定的物料转移到外发车间，并将外发看板的状态更改为"已外发"

     内部看板:
         一般车间
             状态为1、2，可以整数报工/尾数报工。card.workshop.opIndex == session.workshop.opIndex。
             状态为10,可以移库。  card.workshop.opIndex != session.workshop.opIndex
         外发前加工车间
             看板不可以移库到其他车间
             报工前必须检查是否有绑定对应的外发看板，必须检查报工总数是否超过外发看板的收容数。

     外发看板
         绑卡:状态为1，可以在外发前加工车间打卡绑卡。操作后，状态为3.
         外发:状态为3，可以在外发工位上打卡外发。操作后，状态为4.
         报工:状态为4，可以在外发车间打卡报工。操作后，状态10.
*/
@Component
public class WipSessionService implements SessionStepService {
    private final ProductionMovingLogic movingLogic;
    private final ProductRecordLogic productRecordLogic;
    private final WorkstationBindLogic workstationBindLogic;
    private final CardBindLogic cardBindLogic;
    private final RfidCardLogic rfidCardLogic;

    public WipSessionService(ProductionMovingLogic movingLogic, ProductRecordLogic productRecordLogic, WorkstationBindLogic workstationBindLogic, CardBindLogic cardBindLogic, RfidCardLogic rfidCardLogic) {
        this.movingLogic = movingLogic;
        this.productRecordLogic = productRecordLogic;
        this.workstationBindLogic = workstationBindLogic;
        this.cardBindLogic = cardBindLogic;
        this.rfidCardLogic = rfidCardLogic;
    }

    public Command_28 processSession(WorkstationSession session) {
        try {
            RfidCard card = session.getSessionQtyCard();
            if (card.getCardStatus() == RfidCard.CARD_STATUS_NOT_USE) {
                throw new RuntimeException("看板" + card.getRfidNo() + "还没有发卡，必须先发卡以后才可以使用!");
            }
            if (card.getCardType() == RfidCard.CARD_TYPE_INTERNAL) {
                return this.onInternalCard(session);
            }
            return this.onOutsourceCard(session);
        }finally {
            session.setCompleted(); //本session只有一个步骤，所以不管结果如何，都需要结束掉
            session.setLastProcessTime(LocalDateTime.now());
        }
    }

    private Command_28 onOutsourceCard(WorkstationSession session) {
        /*
          外发看板在本车间只能有两种操作:
              1.外发: cardStatus = 3  --> cardStatus = 4
                      1) 更改卡的状态
                      2) 将所绑定的卡的物料，移库到外发车间
              2.回厂(报工): cardStatus = 4  --> cardStatus = 10
                      与内部看板的操作一致

          在前工程车间有1种操作
                绑卡:  cardStatus = 1  --> cardStatus = 3
                      1) 更改卡的状态
                      2) 增加绑定记录

          在后工程车间有1种操作
                投入(移库):  cardStatus = 10 --> cardStatus = 20
                      与内部看板的操作一致
         */
        RfidCard card = session.getSessionQtyCard();
        if (card.getCardStatus() == RfidCard.CARD_STATUS_ISSUED) { //绑卡
            return this.bindOutsourceCard(session);
        }
        if (card.getCardStatus() == RfidCard.CARD_STATUS_BINDED) { //外发
            return this.outsource(session);
        }
        else if (card.getCardStatus() == RfidCard.CARD_STATUS_OUTSOURCED) {  //报工
            return this.reportWip(session, new ProductRecord());
        }else {
            return processStatus10And20(session);
        }
    }

    private Command_28 outsource(WorkstationSession session) {
        if (session.getWorkstation().getWorkshop().getWorkshopType() != Workshop.WORKSHOP_OUTSOURCE) {
            throw new RuntimeException("只可以在外发工位上打卡外发");
        }
        //1.对所有绑定的卡进行移库
        List<RfidCard> qtyCards = rfidCardLogic.getOutsourceBindCard(session.getWorkstationId(), session.getSessionQtyCard().getRecordId());
        int qty = 0;
        for (RfidCard card : qtyCards) {
            ProductionMoving move = new ProductionMoving();
            move.setProductionId(card.getProductionId());
            move.setRfidCardId(card.getRecordId());
            move.setRfidNo(card.getRfidNo());
            move.setWorkshopId(session.getWorkstation().getWorkshopId());
            move.setWorkshopIdFrom(card.getWorkshopId());
            move.setWorkstationId(session.getWorkstationId());
            move.setTimeOfOrigin(LocalDateTime.now());
            move.setGid(session.getGid());
            move.setDid(session.getDid());
            move.setQty(card.getStockQty());

            qty += card.getStockQty();
            movingLogic.reportWipMove(move, ProductionMoving.DIRECTION_ADVANCE);
        }

        RfidCard outsourceCard = session.getSessionQtyCard();
        //2.修改绑定记录的状态
        WorkstationBind bind = workstationBindLogic.get(outsourceCard.getLastBusinessId());
        bind.setBindStatus(WorkstationBind.BIND_STATUS_OUT);
        workstationBindLogic.update(bind);

        //3.更改外发看板卡的状态
        outsourceCard.setCardStatus(RfidCard.CARD_STATUS_OUTSOURCED);
        rfidCardLogic.update(outsourceCard);

        String message = "已外发" + outsourceCard.getProductionName() + qty + "个";
        return Command_28.ok(session.getWorkstation().getDidTemplate(), message);
    }

    private Command_28 bindOutsourceCard(WorkstationSession session) {
        RfidCard outsourceCard = session.getSessionQtyCard();
        Workshop workshop = session.getWorkstation().getWorkshop();
        if (workshop.getWorkshopType() != Workshop.WORKSHOP_QJG) {
            throw new RuntimeException("外发看板只可以在前工程车间绑卡!");
        }
        //1.新增绑定记录
        WorkstationBind bind = new WorkstationBind();
        bind.setAttachTime(LocalDateTime.now());
        bind.setWorkstationId(session.getWorkstationId());
        bind.setOutsourceCardId(outsourceCard.getRecordId());
        bind.setBindStatus(WorkstationBind.BIND_STATUS_BIND);
        bind.setWorkshopId(workshop.getRecordId());
        workstationBindLogic.create(bind);

        //2.更新卡的状态
        outsourceCard.setCardStatus(RfidCard.CARD_STATUS_BINDED);
        outsourceCard.setLastBusinessId(bind.getRecordId());
        rfidCardLogic.update(outsourceCard);

        return Command_28.ok(session.getWorkstation().getDidTemplate(), "已绑定外发看板");
    }

    private Command_28 onInternalCard(WorkstationSession session) {
        RfidCard card = session.getSessionQtyCard();
        Workstation workstation = session.getWorkstation();
        Workshop workshop = workstation.getWorkshop();

        if (card.getCardStatus() == RfidCard.CARD_STATUS_ISSUED || card.getCardStatus() == RfidCard.CARD_STATUS_BACKED) {
            ProductRecord productRecord = new ProductRecord();
            if (workshop.getWorkshopType() != Workshop.WORKSHOP_QJG) {
                return this.reportWip(session, productRecord);//非EV前工程报工
            }
            return this.qjgReportWip(session, productRecord);
        }

        return processStatus10And20(session);
    }

    private Command_28 processStatus10And20(WorkstationSession session) {
        RfidCard card = session.getSessionQtyCard();
        Workshop workshop = session.getWorkstation().getWorkshop();
        if (card.getCardStatus() == RfidCard.CARD_STATUS_REPORTED) {
            return this.moveWip(session);
        }
        //card.getCardStatus() == RfidCard.CARD_STATUS_MOVED
        String kanbanStatus = "看板" + card.getRfidNo() + "的状态：" + card.getCardStatusName();
        if (card.getWorkshop().getOpIndex().equals(workshop.getOpIndex())) {
            throw new RuntimeException(kanbanStatus + ",后工程派发以后才可以报工!");
        } else {
            throw new RuntimeException(kanbanStatus + ",不需要重复移库!");
        }
    }

    private Command_28 qjgReportWip(WorkstationSession session, ProductRecord productRecord) {
        RfidCard card = session.getSessionQtyCard();
        //1.是否有绑定对应的外发看板
        Map<String, Object> bindMap = workstationBindLogic.getWorkstationBindInfo(session.getWorkstation().getRecordId(), card.getProductionId());
        if (bindMap == null || bindMap.get("outSourceProductionId") == null) {
            throw new RuntimeException("工位没有绑定对应产品" + card.getProductionName() + "的外发看板!");
        }
        //2.是否超过外发看板的收容数
        long bindId = (long) bindMap.get("bindId");
        long outsourceCardId = (long) bindMap.get("outsourceCardId");
        int outsourceIssueQty = (int) bindMap.get("outsourceIssueQty");
        int outsourceQty = (int) bindMap.get("outsourceStockQty");
        int reportQty = card.getStockQty();
        if (reportQty + outsourceQty > outsourceIssueQty) {
            throw new RuntimeException("累计报工数量" + (reportQty + outsourceQty) + "已超过外发看板容量" + outsourceIssueQty + ",请换另外一张外发看板");
        }
        //3.报工
        Command_28 result = this.reportWip(session, productRecord);
        //4.增加数量卡与外发看板之间的报工关系
        CardBind cardBind = new CardBind();
        cardBind.setAttachTime(LocalDateTime.now());
        cardBind.setOutsourceCardId(outsourceCardId);
        cardBind.setQtyCardId(card.getRecordId());
        cardBind.setQtyReportId(productRecord.getRecordId());
        cardBind.setWorkstationBindId(bindId);
        cardBindLogic.create(cardBind);
        //5.调整绑定卡的外发数量
        RfidCard outsourceCard = rfidCardLogic.get(outsourceCardId);
        outsourceCard.setOutsourceQty(outsourceCard.getOutsourceQty() + reportQty);
        rfidCardLogic.update(outsourceCard);

        return result;
    }

    private Command_28 moveWip(WorkstationSession session) {
        RfidCard card = session.getSessionQtyCard();
        Workstation workstation = session.getWorkstation();
        Workshop workshop = workstation.getWorkshop();

        if (card.getWorkshop().getOpIndex().equals(workshop.getOpIndex())/*&& card.getCardStatus()==RfidCard.CARD_STATUS_REPORTED*/) {
            String kanbanStatus = "看板" + card.getRfidNo() + "的状态：" + card.getCardStatusName();
            throw new RuntimeException(kanbanStatus + ",不可以重复报工!");
        }

        ProductionMoving moving = session.buildProductionMoving();
        moving.setQty(card.getStockQty());
        movingLogic.reportWipMove(moving, ProductionMoving.DIRECTION_ADVANCE);

        String msg = "已移库" + card.getProductionName() + card.getStockQty() + "个";
        return Command_28.ok(workstation.getDidTemplate(), msg);
    }

    private Command_28 reportWip(WorkstationSession session, ProductRecord productRecord) {
        RfidCard card = session.getSessionQtyCard();
        Workstation workstation = session.getWorkstation();
        Workshop workshop = workstation.getWorkshop();
        if (!card.getWorkshop().getOpIndex().equals(workshop.getOpIndex())) {
            throw new RuntimeException("当前车间为：" + workshop.getWorkshopName() + ",不是看板" + card.getRfidNo() + "可以报工的车间:" + card.getWorkshopName());
        }
        int reportQty = card.getIssueQty();
        if (card.getCardStatus() == RfidCard.CARD_STATUS_BACKED) {
            reportQty = card.getIssueQty() - card.getStockQty();
        }
        String message = WipSessionService.doWipReport(this.productRecordLogic, session, ProductRecord.REPORT_TYPE_WHOLE, reportQty, productRecord);
        return Command_28.ok(workstation.getDidTemplate(), message);
    }

    public static String doWipReport(ProductRecordLogic productRecordLogic, WorkstationSession session, int reportType, int reportQty, ProductRecord productRecord) {
        RfidCard card = session.getSessionQtyCard();
        Workstation workstation = session.getWorkstation();
        Workshop workshop = workstation.getWorkshop();

        productRecord.setWorkshopId(workshop.getRecordId());
        productRecord.setWorkstationId(workstation.getRecordId());
        productRecord.setProductionId(card.getProductionId());
        productRecord.setGid(session.getGid());
        productRecord.setDid(session.getDid());
        productRecord.setRfidCardId(card.getRecordId());
        productRecord.setCardQty(card.getIssueQty());
        productRecord.setQty(reportQty);
        productRecord.setReportType(reportType);
        productRecord.setTimeOfOrigin(LocalDateTime.now());
        productRecord.setOperatorId(session.getOperatorId());

        productRecord.setCard(card);
        productRecordLogic.reportWip(productRecord, workstation.getAutoReportCount());

        Map<String, Integer> summary = productRecordLogic.getReportedSummary(workstation.getRecordId(), productRecord.getTimeOfOriginWork(), card.getProductionId(), productRecord.getShiftId());
        return "已报工" + card.getProductionName() + reportQty + "个,累计" + summary.get("totalCount") + "张，" + summary.get("totalQty") + "个";
    }
}
