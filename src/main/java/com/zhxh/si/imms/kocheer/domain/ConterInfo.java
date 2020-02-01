package com.zhxh.si.imms.kocheer.domain;

import com.zhxh.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConterInfo extends Entity {
    private Long stationID;
    private Integer GID;
    private String conterName;
    private Integer startDID;
    private Integer endDID;
    private String IP;
    private Integer port;
    private String position;
    private Integer isUse;
    private Integer wiressPower;
    private String remark;
}
