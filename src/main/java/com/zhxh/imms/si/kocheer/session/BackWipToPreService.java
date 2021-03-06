package com.zhxh.imms.si.kocheer.session;

import com.zhxh.imms.data.BusinessException;
import com.zhxh.imms.mes.mfc.domain.ProductionMoving;
import com.zhxh.imms.mes.mfc.domain.RfidCard;
import com.zhxh.imms.mes.mfc.logic.ProductionMovingLogic;
import com.zhxh.imms.mes.org.domain.Operator;
import com.zhxh.imms.mes.org.logic.WorkshopLogic;
import com.zhxh.imms.si.kocheer.ReqDataConstants;
import com.zhxh.imms.si.kocheer.command.Command_28;
import com.zhxh.imms.si.kocheer.domain.WorkstationSession;
import com.zhxh.imms.si.kocheer.domain.WorkstationSessionStep;
import org.springframework.stereotype.Component;

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
            throw new BusinessException("请刷工卡");
        }
        if (card.getCardStatus() != RfidCard.CARD_STATUS_MOVED) {
            throw new BusinessException("必须是已移库的看板才可以退还");
        }
        if (!card.getWorkshop().getOpIndex().equals(session.getWorkstation().getWorkshop().getOpIndex())) {
            throw new BusinessException("只能在看板的报工车间" + card.getWorkshopName() + "才可以退还");
        }
        return Command_28.ok(session.getWorkstation().getDidTemplate(), "请输入退还数量");
    }

    private Command_28 back_3(WorkstationSession session) {
        if (session.getCurrentReqType() != ReqDataConstants.REQ_TYPE_KEY_SINGLE
                && session.getCurrentReqType() != ReqDataConstants.REQ_TYPE_KEY_MULTI) {
            throw new BusinessException("请输入退还数量");
        }

        RfidCard card = session.getSessionQtyCard();
        int backQty = session.getQtyFromReqData(session.getCurrentReqData(), WorkstationSession.QTY_TYPE_REQUIRED);

        if (backQty > card.getStockQty()) {
            throw new BusinessException("退还数必须 ≤ 报工数量");
        }
        return Command_28.ok(session.getWorkstation().getDidTemplate(), "接收人刷工卡确认");
    }

    private Command_28 back_4(WorkstationSession session) {
        Operator recvOperator = session.getCurrentOperator();
        if (recvOperator == null) {
            throw new BusinessException("请接收人刷工卡确认");
        }

        if (recvOperator.getEmployeeCardNo().equals(session.getOperator().getEmployeeCardNo())) {
            throw new BusinessException("接收人和退还人不能是同一个人");
        }

        //移库报工
        WorkstationSessionStep step = session.getStep(3);
        int backQty = session.getQtyFromReqData(step.getReqData(), WorkstationSession.QTY_TYPE_ISSUE);
        ProductionMoving moving = session.buildProductionMoving();
        moving.setQty(backQty);
        long lastMovingId = session.getSessionQtyCard().getLastBusinessId();
        ProductionMoving lastMoving = movingLogic.get(lastMovingId);
        long fromWorkshopId = lastMoving.getWorkshopId();
        moving.setWorkshopIdFrom(fromWorkshopId);
        movingLogic.reportWipMove(moving, ProductionMoving.DIRECTION_BACK);

        //完成Session
        session.complete();

        //返回Session
        String msg = "已退还" + session.getSessionQtyCard().getProductionName() + backQty + "个";
        return Command_28.ok(session.getWorkstation().getDidTemplate(), msg);
    }
}
