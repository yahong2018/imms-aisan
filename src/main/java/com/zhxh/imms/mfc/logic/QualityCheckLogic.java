package com.zhxh.imms.mfc.logic;

import com.zhxh.imms.mfc.domain.QualityCheck;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QualityCheckLogic extends WorkshopReportLogic<QualityCheck> {
    @Override
    protected QualityCheck createRecord() {
        return new QualityCheck();
    }

    public void batchCreate(List<QualityCheck> checkList) {
        for (QualityCheck qualityCheck : checkList) {
            this.reportWip(qualityCheck, 0);
        }
    }
}
