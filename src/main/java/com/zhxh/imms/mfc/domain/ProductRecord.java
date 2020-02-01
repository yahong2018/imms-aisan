package com.zhxh.imms.mfc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhxh.data.domain.Entity;
import com.zhxh.si.imms.wdb.TookPlaceTimeRecord;
import com.zhxh.imms.mfc.logic.WorkDayAndShift;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductRecord extends WorkshopReportRecord implements TookPlaceTimeRecord {
    private Long workstationId;
    private String workstationCode;
    private String workstationName;
    private String wocgCode;

    private Integer gid;
    private Integer did;
    private LocalDateTime timeOfOrigin;

    private Long rfidCardId;
    private String rfidCardNo;

    private Integer cardQty;
    private Long operatorId;
    private String employeeId;
    private String employeeName;
    private String remark;

    @JsonIgnore
    private RfidCard card;

    public void fillWorkShift(){
        WorkDayAndShift workDayAndShift = new WorkDayAndShift(this.getTimeOfOrigin());
        this.setShiftId(workDayAndShift.getShiftId());
        this.setTimeOfOriginWork(workDayAndShift.getTimeOfOriginalWork());
    }

    @Override
    public LocalDateTime getTookPlaceTime() {
        return this.timeOfOrigin;
    }
}
