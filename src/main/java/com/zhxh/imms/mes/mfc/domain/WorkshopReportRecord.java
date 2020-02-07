package com.zhxh.imms.mes.mfc.domain;

import com.zhxh.imms.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WorkshopReportRecord extends Entity {
    private Long workshopId;
    private String workshopCode;
    private String workshopName;

    private Long productionId;
    private String productionCode;
    private String productionName;

    private Integer qty;
    private Integer reportType;

    private Integer shiftId;
    private LocalDate timeOfOriginWork;

    public final static int REPORT_TYPE_WHOLE = 0;  //整箱报工
    public final static int REPORT_TYPE_PARTIAL = 1;//尾数报工
    public final static int REPORT_TYPE_AUTO = 99;//自动报工
    public final static int REPORT_TYPE_QC = 100;//品质报工
}
