package com.zhxh.imms.mfc.controller;

import com.zhxh.data.CrudLogic;
import com.zhxh.imms.mfc.domain.ProductRecord;
import com.zhxh.imms.mfc.logic.ProductRecordLogic;
import com.zhxh.web.CrudController;
import com.zhxh.web.OrgFilterController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/mfc/productRecord")
public class ProductRecordController extends OrgFilterController<ProductRecord> {
    @Autowired
    private ProductRecordLogic productRecordLogic;

    @Override
    protected CrudLogic<ProductRecord> getLogic() {
        return productRecordLogic;
    }

    @Override
    public int create(ProductRecord item) throws RuntimeException {
        if (item.getReportType() == null) {
            item.setReportType(ProductRecord.REPORT_TYPE_WHOLE);
        }
        productRecordLogic.reportWip(item);

        return 1;
    }
}
