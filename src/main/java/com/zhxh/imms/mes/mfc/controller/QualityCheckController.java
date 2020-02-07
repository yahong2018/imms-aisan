package com.zhxh.imms.mes.mfc.controller;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.mes.mfc.domain.QualityCheck;
import com.zhxh.imms.mes.mfc.logic.QualityCheckLogic;
import com.zhxh.imms.mes.mfc.model.QualityCheckBatchAddForm;
import com.zhxh.imms.web.OrgFilterController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/imms/mes/mfc/qualityCheck")
public class QualityCheckController extends OrgFilterController<QualityCheck> {
    @Autowired
    private QualityCheckLogic qualityCheckLogic;

    @Override
    protected CrudLogic<QualityCheck> getLogic() {
        return this.qualityCheckLogic;
    }

    @Override
    public int create(QualityCheck item) {
        item.setReportType(QualityCheck.REPORT_TYPE_QC);
        return this.qualityCheckLogic.reportWip(item, 0);
    }

    @PostMapping("batchAdd")
    public int batchAdd(QualityCheckBatchAddForm batchAddForm)
    {
        List<QualityCheck> checkList = batchAddForm.toQualityRecords();
        qualityCheckLogic.batchCreate(checkList);
        return checkList.size();
    }
}
