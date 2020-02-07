package com.zhxh.imms.mes.mfc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhxh.imms.data.domain.Entity;
import com.zhxh.imms.si.wdb.TookPlaceTimeRecord;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductionMoving extends Entity implements TookPlaceTimeRecord {
    private Long productionId;
    private String productionCode;
    private String productionName;

    private String rfidNo;
    private Long rfidCardId;

    private Long workshopId;
    private String workshopCode;
    private String workshopName;

    private Long workstationId;
    private String workstationCode;
    private String workstationName;
    private String wocgCode;

    private Long workshopIdFrom;
    private String workshopCodeFrom;
    private String workshopNameFrom;

    private Integer gid;
    private Integer did;

    private Long operatorId;
    private String employeeId;
    private String employeeName;

    private Long operatorIdFrom;
    private String employeeIdFrom;
    private String employeeNameFrom;

    private Integer qty;
    private LocalDateTime timeOfOrigin;
    private LocalDate timeOfOriginWork;
    private Integer shiftId;

    public void fillWorkShift(){
        WorkDayAndShift workDayAndShift = new WorkDayAndShift(this.getTimeOfOrigin());
        this.setShiftId(workDayAndShift.getShiftId());
        this.setTimeOfOriginWork(workDayAndShift.getTimeOfOriginalWork());
    }

    @JsonIgnore
    private RfidCard card;

    public final static int DIRECTION_ADVANCE = 0;
    public final static int DIRECTION_BACK = 1;

    @Override
    public LocalDateTime getTookPlaceTime() {
        return this.timeOfOrigin;
    }
}
