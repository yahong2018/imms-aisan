package com.zhxh.imms.mfc.domain;

import com.zhxh.si.imms.wdb.TookPlaceTimeRecord;
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
