package com.zhxh.si.imms.kocheer.session;

import com.zhxh.imms.mfc.domain.ProductionMoving;
import com.zhxh.imms.mfc.domain.RfidCard;
import com.zhxh.imms.mfc.logic.ProductionMovingLogic;
import com.zhxh.imms.mfc.logic.RfidCardLogic;
import com.zhxh.imms.mfc.logic.WorkDayAndShift;
import com.zhxh.imms.org.domain.Operator;
import com.zhxh.imms.org.domain.Workshop;
import com.zhxh.imms.org.logic.WorkshopLogic;
import com.zhxh.si.imms.kocheer.ReqDataConstants;
import com.zhxh.si.imms.kocheer.command.Command_28;
import com.zhxh.si.imms.kocheer.domain.WorkstationSession;
import com.zhxh.si.imms.kocheer.domain.WorkstationSessionStep;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BackWipToPreService implements SessionStepService {
    private final WorkshopLogic workshopLogic;

    private final ProductionMovingLogic movingLogic;

    public BackWipToPreService(WorkshopLogic workshopLogic, ProductionMovingLogic movingLogic) {
        this.workshopLogic = workshopLogic;
        this.movingLogic = movingLogic;
    }

    public Command_28 processSession(WorkstationSession session) {
        if (session.getCurrentStep() == WorkstationSession.SESSION_STEP_INIT) {  //第一步:提示刷看板
            return Command_28.ok(session.getWorkstation().getDidTemplate(), "请刷看板");
        } else if (session.getCurrentStep() == 1) { //第二步:验证看板，提示输入退还数量
            return this.back_2(session);
        } else if (session.getCurrentStep() == 2) { //第三步，验证数量，提示刷接收人工卡
            return this.back_3(session);
        } else {   //验证工卡，完成派发
            return this.back_4(session);
        }
    }

    private Command_28 back_2(WorkstationSession session) {
        //1.看板必须是已移库的看板
        //2.工位必须是看板的报工工位
        RfidCard card = session.getSessionQtyCard();
        if (card == null) {
            throw new RuntimeException("请刷工卡");
        }
        if (card.getCardStatus() != RfidCard.CARD_STATUS_MOVED) {
            throw new RuntimeException("必须是已移库的看板才可以退还");
        }
        if (!card.getWorkshop().getOpIndex().equals(session.getWorkstation().getWorkshop().getOpIndex())) {
            throw new RuntimeException("只能在看板的报工车间" + card.getWorkshopName() + "才可以退还");
        }
        return Command_28.ok(session.getWorkstation().getDidTemplate(), "请输入退还数量");
    }

    private Command_28 back_3(WorkstationSession session) {
        if (session.getCurrentReqType() != ReqDataConstants.REQ_TYPE_KEY_SINGLE
                && session.getCurrentReqType() != ReqDataConstants.REQ_TYPE_KEY_MULTI) {
            throw new RuntimeException("请输入退还数量");
        }

        RfidCard card = session.getSessionQtyCard();
        int backQty = session.getQtyFromReqData(session.getCurrentReqData());

        if (backQty > card.getStockQty()) {
            throw new RuntimeException("退还数必须 ≤ 报工数量");
        }
        return Command_28.ok(session.getWorkstation().getDidTemplate(), "接收人刷工卡确认");
    }

    private Command_28 back_4(WorkstationSession session) {
        Operator recvOperator = session.getCurrentOperator();
        if (recvOperator == null) {
            throw new RuntimeException("请接收人刷工卡确认");
        }

        if (recvOperator.getEmployeeCardNo().equals(session.getOperator().getEmployeeCardNo())) {
            throw new RuntimeException("接收人和退还人不能是同一个人");
        }

        //移库报工
        WorkstationSessionStep step = session.getStep(3);
        int backQty = session.getQtyFromReqData(step.getReqData());
        ProductionMoving moving = session.buildProductionMoving();
        moving.setQty(backQty);
        long lastMovingId = session.getSessionQtyCard().getLastBusinessId();
        ProductionMoving lastMoving = movingLogic.get(lastMovingId);
        long fromWorkshopId = lastMoving.getWorkshopId();
        moving.setWorkshopIdFrom(fromWorkshopId);
        movingLogic.reportWipMove(moving, ProductionMoving.DIRECTION_BACK);

        String msg = "已退还" + session.getSessionQtyCard().getProductionName() + backQty + "个";
        return Command_28.ok(session.getWorkstation().getDidTemplate(), msg);
    }
}
