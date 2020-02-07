package com.zhxh.imms.mes.mfc.model;

import com.zhxh.imms.mes.mfc.domain.QualityCheck;
import com.zhxh.imms.mes.mfc.domain.WorkshopReportRecord;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QualityCheckBatchAddForm {
    private Long defectId;
    private String productionId;
    private Long workshopId;
    private String wocgCode;
    private String locCode;
    private Integer shiftId;
    private LocalDate timeOfOriginWork;
    private Integer qty;

    public List<QualityCheck> toQualityRecords() {
        QualityCheckBatchAddForm batchAddForm = this;
        String[] productionIdList = batchAddForm.productionId.split(";");
        List<QualityCheck> checkList = new ArrayList<>();
        for (int i = 0; i < productionIdList.length; i++) {
            QualityCheck quality = new QualityCheck();
            quality.setDefectId(batchAddForm.defectId);
            quality.setProductionId(Long.parseLong(productionIdList[i]));
            quality.setWocgCode(batchAddForm.wocgCode);
            quality.setLocCode(batchAddForm.wocgCode);
            quality.setShiftId(batchAddForm.shiftId);
            quality.setQty(batchAddForm.qty);
            quality.setTimeOfOriginWork(batchAddForm.timeOfOriginWork);
            quality.setWorkshopId(batchAddForm.workshopId);

            quality.setReportType(WorkshopReportRecord.REPORT_TYPE_QC);

            checkList.add(quality);
        }

        return checkList;
    }
}
