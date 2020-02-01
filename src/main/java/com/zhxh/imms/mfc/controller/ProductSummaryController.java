package com.zhxh.imms.mfc.controller;

import com.zhxh.data.CrudLogic;
import com.zhxh.imms.mfc.domain.ProductSummary;
import com.zhxh.imms.mfc.logic.ProductSummaryLogic;
import com.zhxh.web.CrudController;
import com.zhxh.web.OrgFilterController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/mfc/productSummary")
public class ProductSummaryController extends OrgFilterController<ProductSummary> {
    @Autowired
    private ProductSummaryLogic productSummaryLogic;

    @Override
    protected CrudLogic<ProductSummary> getLogic() {
        return productSummaryLogic;
    }
}
