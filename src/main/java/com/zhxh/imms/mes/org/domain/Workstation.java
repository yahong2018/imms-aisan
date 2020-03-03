package com.zhxh.imms.mes.org.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhxh.imms.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Workstation extends Entity {
    private String workstationCode;
    private String workstationName;
    private String description;
    private Long workshopId;
    private Integer gid;
    private Integer did;
    private Integer didTemplate;
    private String wocgCode;
    private Integer autoReportCount;
    private String locCode;

    private boolean canReport;//报工工位
    private boolean canMoveIn;//投入工位
    private boolean canIssueCard;//发卡工位
    private boolean canOutsourceOut;//外发出厂工位
    private boolean canOutsourceBack;//外发回厂工位

    @JsonIgnore
    private com.zhxh.imms.mes.org.domain.Workshop workshop;
}
