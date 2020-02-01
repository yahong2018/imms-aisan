package com.zhxh.si.imms.wdb.controller;

import com.google.gson.GsonBuilder;
import com.zhxh.imms.mfc.domain.ProductionMoving;
import com.zhxh.imms.mfc.domain.RfidCard;
import com.zhxh.imms.mfc.logic.ProductionMovingLogic;
import com.zhxh.imms.mfc.logic.RfidCardLogic;
import com.zhxh.imms.org.domain.Operator;
import com.zhxh.imms.org.domain.Workshop;
import com.zhxh.imms.org.logic.OperatorLogic;
import com.zhxh.imms.org.logic.WorkshopLogic;
import com.zhxh.si.imms.wdb.wdto.GwInstoreItem;
import com.zhxh.utils.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/mfc/productionOrderProgress")
public class InstoreController {
    private final WorkshopLogic workshopLogic;
    private final RfidCardLogic rfidCardLogic;
    private final ProductionMovingLogic productionMovingLogic;
    private final OperatorLogic operatorLogic;

    public InstoreController(ProductionMovingLogic productionMovingLogic, RfidCardLogic rfidCardLogic, WorkshopLogic workshopLogic, OperatorLogic operatorLogic) {
        this.productionMovingLogic = productionMovingLogic;
        this.rfidCardLogic = rfidCardLogic;
        this.workshopLogic = workshopLogic;
        this.operatorLogic = operatorLogic;
    }

    @PostMapping("reportInstroeByErp")
    public int reportInstoreByERP(@RequestBody String json) {
        Logger.info("收到公务移库信息：" + json);
        GwInstoreItem gwInstoreItem = new GsonBuilder().create().fromJson(json, GwInstoreItem.class);

        RfidCard card = verifyRfidCard(gwInstoreItem);
        Workshop gwWorkshop = workshopLogic.getGW();
        Operator operator = operatorLogic.getByEmployeeCard(gwInstoreItem.getOperatorCode());

        ProductionMoving moving = new ProductionMoving();
        moving.setRfidCardId(card.getRecordId());
        moving.setRfidNo(card.getRfidNo());
        moving.setCard(card);
        moving.setProductionId(card.getProductionId());
        moving.setWorkshopId(gwWorkshop.getRecordId());
        moving.setWorkshopIdFrom(card.getWorkshopId());
        moving.setQty(gwInstoreItem.getQty());
        if (operator != null) {
            moving.setOperatorId(operator.getRecordId());
        }
        productionMovingLogic.reportWipMove(moving, ProductionMoving.DIRECTION_ADVANCE);

        Logger.info("移库信息处理完毕");
        return 0;
    }

    private RfidCard verifyRfidCard(GwInstoreItem gwInstoreItem) {
        RfidCard card = rfidCardLogic.getByKanbanAndMaterial(gwInstoreItem.getKanbanNo(), gwInstoreItem.getProductionCode());
        if (card == null) {
            throw new RuntimeException("看板编号(KanbanNo)=" + gwInstoreItem.getKanbanNo() + "的看板还没有发卡!");
        }
        if (card.getCardStatus() != RfidCard.CARD_STATUS_REPORTED) {
            throw new RuntimeException("看板编号(KanbanNo)='" + gwInstoreItem.getKanbanNo() + "的看板还没有报工，不可以执行移库动!");
        }
        Workshop workshop = workshopLogic.get(card.getWorkshopId());
        card.setWorkshop(workshop);
        return card;
    }
}
