package com.zhxh.imms.si.kocheer.session;

import com.zhxh.imms.data.BusinessException;
import com.zhxh.imms.mes.mfc.domain.RfidCard;
import com.zhxh.imms.mes.mfc.logic.RfidCardLogic;
import com.zhxh.imms.si.kocheer.ReqDataConstants;
import com.zhxh.imms.si.kocheer.command.Command_28;
import com.zhxh.imms.si.kocheer.domain.WorkstationSession;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class IssueCardService implements SessionStepService {
    private final RfidCardLogic rfidCardLogic;

    public IssueCardService(RfidCardLogic rfidCardLogic) {
        this.rfidCardLogic = rfidCardLogic;
    }

    public Command_28 processSession(WorkstationSession session) {
        if (session.getCurrentStep() == WorkstationSession.SESSION_STEP_INIT) {  //第一步:提示刷看板
            return Command_28.ok(session.getWorkstation().getDidTemplate(), "请刷看板");
        } else if (session.getCurrentStep() == 1) { //第二步:验证看板，提示输入派发数量
            return this.issue_2(session);
        } else { //第三步，验证数量、发卡
            return this.issue_3(session);
        }
    }

    private Command_28 issue_2(WorkstationSession session) {
        if (session.getCurrentReqType() != ReqDataConstants.REQ_TYPE_WIP_CARD) {
            throw new BusinessException("请刷看板");
        }
        RfidCard card = session.getSessionQtyCard();
        if (card.getCardStatus() != RfidCard.CARD_STATUS_NOT_USE && card.getCardStatus() != RfidCard.CARD_STATUS_MOVED) {
            throw new BusinessException("只有未使用和已移库的看板才可以派发");
        }
        String message = "按确定使用上次数量" + card.getIssueQty() + "\n如需自定义派发数量,先按数字键，再按确定";
        return Command_28.ok(session.getWorkstation().getDidTemplate(), message);
    }

    private Command_28 issue_3(WorkstationSession session) {
        if (session.getCurrentReqType() != ReqDataConstants.REQ_TYPE_KEY_SINGLE
                && session.getCurrentReqType() != ReqDataConstants.REQ_TYPE_KEY_MULTI
        ) {
            throw new BusinessException("请输入派发数量");
        }
        RfidCard card = session.getSessionQtyCard();
        int issueQty = session.getQtyFromReqData(session.getCurrentReqData(),WorkstationSession.QTY_TYPE_ISSUE);

        //1.派发
        card.setIssueQty(issueQty);
        card.setStockQty(0);
        card.setOutsourceQty(0);
        card.setLastBusinessId(0L);
        card.setCardStatus(RfidCard.CARD_STATUS_ISSUED);
        rfidCardLogic.update(card);

        //2.构建返回结果
        Map<String, Integer> totalMap = rfidCardLogic.getIssuedTotalCountAndQty(card.getProductionId());
        String message = "已给看板" + card.getRfidNo() + "派发" + card.getProductionName() + issueQty + "个"
                + ",共计" + totalMap.get("totalCount") + "张," + totalMap.get("totalQty") + "个,继续发卡请刷其他看板";
        Command_28 result = Command_28.ok(session.getWorkstation().getDidTemplate(), message);

        //3.关闭前一Session,
        session.setCurrentStep(WorkstationSession.SESSION_STEP_FINISHED);
        //4. 结果中加入新的session
        WorkstationSession theNewSession = session.startNewSession(1,"发卡自动创建");
        result.setTag(theNewSession);

        return result;
    }


}
