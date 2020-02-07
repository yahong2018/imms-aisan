package com.zhxh.imms.mes.mfc.domain;

import com.zhxh.imms.si.wdb.TookPlaceTimeRecord;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class QualityCheck extends WorkshopReportRecord implements TookPlaceTimeRecord {
    private String wocgCode;
    private String locCode;

    private Long defectId;
    private String defectCode;
    private String defectName;

    @Override
    public LocalDateTime getTookPlaceTime() {
        return LocalDateTime.of(this.getTimeOfOriginWork(), LocalTime.MIN);
    }
}
