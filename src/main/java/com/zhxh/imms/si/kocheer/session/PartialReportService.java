package com.zhxh.imms.si.kocheer.session;

import com.zhxh.imms.data.BusinessException;
import com.zhxh.imms.mes.mfc.domain.ProductRecord;
import com.zhxh.imms.mes.mfc.domain.RfidCard;
import com.zhxh.imms.mes.mfc.logic.ProductRecordLogic;
import com.zhxh.imms.si.kocheer.ReqDataConstants;
import com.zhxh.imms.si.kocheer.command.Command_28;
import com.zhxh.imms.si.kocheer.domain.WorkstationSession;
import org.springframework.stereotype.Component;

@Component
public class PartialReportService implements SessionStepService {
    private final ProductRecordLogic productRecordLogic;

    public PartialReportService(ProductRecordLogic productRecordLogic) {
        this.productRecordLogic = productRecordLogic;
    }

    public Command_28 processSession(WorkstationSession session) {
        if (session.getCurrentStep() == WorkstationSession.SESSION_STEP_INIT) {  //第一步:提示刷看板
            return Command_28.ok(session.getWorkstation().getDidTemplate(), "请刷看板");
        } else if (session.getCurrentStep() == 1) { //第二步:验证看板，提示输入报工数量
            return this.partialReport_2(session);
        } else { //第三步，验证数量、报工
            return this.partialReport_3(session);
        }
    }

    private Command_28 partialReport_2(WorkstationSession session) {
        if (session.getCurrentReqType() != ReqDataConstants.REQ_TYPE_WIP_CARD) {
            throw new BusinessException("请刷看板");
        }
        RfidCard card = session.getSessionQtyCard();
        if (card.getCardStatus() != RfidCard.CARD_STATUS_ISSUED && card.getCardStatus() != RfidCard.CARD_STATUS_REPORTED) {
            throw new BusinessException("只有已派发和已报工的看板才可以进行尾数报工");
        }
        String message = "按确定报剩余" + (card.getIssueQty() - card.getStockQty()) + "个\n如需修改，请输入尾数再按确定";
        return Command_28.ok(session.getWorkstation().getDidTemplate(), message);
    }

    private Command_28 partialReport_3(WorkstationSession session) {
        if (session.getCurrentReqType() != ReqDataConstants.REQ_TYPE_KEY_SINGLE
                && session.getCurrentReqType() != ReqDataConstants.REQ_TYPE_KEY_MULTI
        ) {
            throw new BusinessException("请输入报工数量");
        }

        int reportQty = session.getQtyFromReqData(session.getCurrentReqData(), WorkstationSession.QTY_TYPE_REDUCE);
        if (reportQty + session.getSessionQtyCard().getStockQty() > session.getSessionQtyCard().getIssueQty()) {
            throw new BusinessException("累计数量大于收容数,请输入正确的报工数量");
        }
        ProductRecord productRecord = new ProductRecord();
        String message = WipSessionService.doWipReport(this.productRecordLogic, session, ProductRecord.REPORT_TYPE_PARTIAL, reportQty, productRecord);
        session.complete();
        return Command_28.ok(session.getWorkstation().getDidTemplate(), message);
    }
}
