package com.zhxh.imms.mes.mfc.controller;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.mes.mfc.domain.ProductRecord;
import com.zhxh.imms.mes.mfc.logic.ProductRecordLogic;
import com.zhxh.imms.web.OrgFilterController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/mes/mfc/productRecord")
public class ProductRecordController extends OrgFilterController<ProductRecord> {
    private final ProductRecordLogic productRecordLogic;

    public ProductRecordController(ProductRecordLogic productRecordLogic) {
        this.productRecordLogic = productRecordLogic;
    }

    @Override
    protected CrudLogic<ProductRecord> getLogic() {
        return productRecordLogic;
    }

    @Override
    public int create(ProductRecord item) {
        if (item.getReportType() == null) {
            item.setReportType(ProductRecord.REPORT_TYPE_WHOLE);
        }
        productRecordLogic.reportWip(item);

        return 1;
    }
}
