package com.zhxh.imms.mes.mfc.controller;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.mes.mfc.domain.ProductSummary;
import com.zhxh.imms.mes.mfc.logic.ProductSummaryLogic;
import com.zhxh.imms.web.OrgFilterController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/mes/mfc/productSummary")
public class ProductSummaryController extends OrgFilterController<ProductSummary> {
    @Autowired
    private ProductSummaryLogic productSummaryLogic;

    @Override
    protected CrudLogic<ProductSummary> getLogic() {
        return productSummaryLogic;
    }
}
