package com.zhxh.imms.si.kocheer.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhxh.imms.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConterInfo extends Entity {
    @JsonProperty(value="stationID")
    private Long stationID;

    private String stationName;

    @JsonProperty(value="GID")
    private Integer GID;

    @JsonProperty(value="conterName")
    private String conterName;

    @JsonProperty(value="startDID")
    private Integer startDID;

    @JsonProperty(value="endDID")
    private Integer endDID;

    @JsonProperty(value="IP")
    private String IP;

    @JsonProperty(value="port")
    private Integer port;

    @JsonProperty(value="position")
    private String position;

    @JsonProperty(value="isUse")
    private Integer isUse;

    @JsonProperty(value="wiressPower")
    private Integer wiressPower;

    @JsonProperty(value="remark")
    private String remark;
}
