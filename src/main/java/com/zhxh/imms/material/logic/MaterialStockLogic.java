package com.zhxh.imms.material.logic;

import com.zhxh.data.CrudLogic;
import com.zhxh.data.DbQueryParameter;
import com.zhxh.imms.material.domain.MaterialStock;
import com.zhxh.web.FilterExpression;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MaterialStockLogic extends CrudLogic<MaterialStock> {
    public MaterialStock getMaterialStock(Long workshopId, Long materialId) {
        DbQueryParameter query = new DbQueryParameter();
        FilterExpression[] expr = new FilterExpression[2];
        expr[0] = new FilterExpression("storeId", "=", workshopId);
        expr[1] = new FilterExpression("materialId", "=", materialId, "and");
        FilterExpression.fillWhere(MaterialStock.class, query, expr);

        List<MaterialStock> stocks = this.getAll(query);
        if (stocks.size() > 0) {
            return stocks.get(0);
        }

        return null;
    }

    public synchronized MaterialStock assureStock(Long workshopId, Long materialId) throws RuntimeException {
        MaterialStock stock = this.getMaterialStock(workshopId, materialId);
        if (stock == null) {
            stock = new MaterialStock();
            stock.setStoreId(workshopId);
            stock.setMaterialId(materialId);
            stock.setQtyStock(0);
            stock.setQtyMoveIn(0);
            stock.setQtyBackIn(0);
            stock.setQtyBackOut(0);
            stock.setQtyConsumeGood(0);
            stock.setQtyConsumeDefect(0);
            stock.setQtyGood(0);
            stock.setQtyDefect(0);
            stock.setQtyMoveOut(0);

            this.create(stock);
        }
        return stock;
    }
}
