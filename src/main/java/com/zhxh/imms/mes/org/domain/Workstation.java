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
    private Integer workstationType;
    private String description;
    private Long workshopId;
    private Integer gid;
    private Integer did;
    private Integer didTemplate;
    private String wocgCode;
    private Integer autoReportCount;
    private String locCode;

    @JsonIgnore
    private com.zhxh.imms.mes.org.domain.Workshop workshop;

    public final static int WORKSTATION_NORMAL = 1;
    public final static int WORKSTATION_ISSUE_CARD = 2;
    public final static int WORKSTATION_INPUT = 3;
    public final static int WORKSTATION_OUTPUT = 4;
}
