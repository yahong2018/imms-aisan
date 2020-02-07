package com.zhxh.imms.mes.mfc.logic;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.data.DbQueryParameter;
import com.zhxh.imms.mes.mfc.domain.ProductSummary;
import com.zhxh.imms.web.FilterExpression;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ProductSummaryLogic extends CrudLogic<ProductSummary> {
    public ProductSummary getProductSummary(Long productionId, Long workshopId, LocalDate productDate) {
        FilterExpression[] exprList = new FilterExpression[3];
        exprList[0] = new FilterExpression("productDate", "=", productDate);
        exprList[1] = new FilterExpression("productionId", "=", productionId, "and");
        exprList[2] = new FilterExpression("workshopId", "=", workshopId, "and");
        DbQueryParameter query = new DbQueryParameter();
        FilterExpression.fillWhere(ProductSummary.class, query, exprList);

        List<ProductSummary> productSummaryList = this.getAll(query);
        if (productSummaryList.size() > 0) {
            return productSummaryList.get(0);
        }
        return null;
    }

    public synchronized ProductSummary assureProductSummary(Long productionId, Long workshopId, LocalDate productDate) {
        ProductSummary productSummary = this.getProductSummary(productionId, workshopId, productDate);
        if (productSummary == null) {
            productSummary = buildProductSummary(productionId, workshopId, productDate);
        }
        return productSummary;
    }

    private ProductSummary buildProductSummary(Long productionId, Long workshopId, LocalDate productDate) {
        ProductSummary productSummary = new ProductSummary();
        productSummary.setProductDate(productDate);
        productSummary.setWorkshopId(workshopId);
        productSummary.setProductionId(productionId);
        productSummary.setQtyGood0(0);
        productSummary.setQtyGood1(0);
        productSummary.setQtyDefect0(0);
        productSummary.setQtyDefect1(0);

        this.create(productSummary);
        return productSummary;
    }
}
