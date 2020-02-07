package com.zhxh.imms.mes.mfc.logic;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.mes.material.domain.MaterialStock;
import com.zhxh.imms.mes.material.logic.MaterialStockLogic;
import com.zhxh.imms.mes.mfc.domain.ProductionMoving;
import com.zhxh.imms.mes.mfc.domain.RfidCard;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductionMovingLogic extends CrudLogic<ProductionMoving> {
    private final RfidCardLogic cardLogic;
    private final MaterialStockLogic materialStockLogic;

    public ProductionMovingLogic(RfidCardLogic cardLogic, MaterialStockLogic materialStockLogic) {
        this.cardLogic = cardLogic;
        this.materialStockLogic = materialStockLogic;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public synchronized int reportWipMove(ProductionMoving moving, int direction) {
        /*
        1. 修改rfid卡的状态
        2. 更新库存:移入部门增加库存，移出部门减少
        3. 新增库存移动记录
        */
        moving.fillWorkShift();
        int result = this.create(moving);
        this.updateCard(moving,direction);
        this.adjustStock(moving, direction);

        return result;
    }

    private void adjustStock(ProductionMoving moving, int direction) {
        this.adjustMoveInStock(moving, direction);
        this.adjustMoveOutStock(moving, direction);
    }

    private void adjustMoveOutStock(ProductionMoving moving, int direction) {
        //上部门移出、本部门退出
        MaterialStock stock = this.materialStockLogic.assureStock(moving.getWorkshopIdFrom(), moving.getProductionId());
        int qtyStock = stock.getQtyStock() - moving.getQty();
        stock.setQtyStock(qtyStock);
        if (direction == ProductionMoving.DIRECTION_ADVANCE) {
            int qtyMoveOut = stock.getQtyMoveOut() + moving.getQty();
            stock.setQtyMoveOut(qtyMoveOut);
        } else {
            int qtyBackOut = stock.getQtyBackOut() + moving.getQty();
            stock.setQtyBackOut(qtyBackOut);
        }
        this.materialStockLogic.update(stock);
    }

    private void adjustMoveInStock(ProductionMoving moving, int direction) {
        //上部门移入、下部门退回
        MaterialStock stock = materialStockLogic.assureStock(moving.getWorkshopId(), moving.getProductionId());
        int qtyStock = stock.getQtyStock() + moving.getQty();
        stock.setQtyStock(qtyStock);
        if (direction == ProductionMoving.DIRECTION_ADVANCE) {
            int qtyMoveIn = stock.getQtyMoveIn() + moving.getQty();
            stock.setQtyMoveIn(qtyMoveIn);
        } else {
            int qtyBackIn = stock.getQtyBackIn() + moving.getQty();
            stock.setQtyBackIn(qtyBackIn);
        }
        this.materialStockLogic.update(stock);
    }


    private void updateCard(ProductionMoving moving,int direction) {
        RfidCard card = moving.getCard();
        if (card == null) {
            if (moving.getRfidCardId() == null) {
                return;
            }
            card = cardLogic.get(moving.getRfidCardId());
            if (card == null) {
                return;
            }
        }
        card.setLastBusinessId(moving.getRecordId());

        if(direction == ProductionMoving.DIRECTION_BACK){
            card.setStockQty(moving.getQty());
            card.setCardStatus(RfidCard.CARD_STATUS_BACKED);
        }else{
            card.setCardStatus(RfidCard.CARD_STATUS_MOVED);
        }

        this.cardLogic.update(card);
    }
}
