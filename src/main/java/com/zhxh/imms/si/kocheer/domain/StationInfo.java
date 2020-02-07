package com.zhxh.imms.si.kocheer.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhxh.imms.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StationInfo extends Entity {
    //private Integer stationID;

    private String stationCode;
    private String stationName;
    private String stationPosition;
    private String softWareName;
    private String softWareVersion;

    @JsonProperty(value="stationIP")
    private String stationIP;

    private Integer stationLoginState;

    @JsonProperty(value="loginUserID")
    private Long loginUserID;

    private String loginName;
    private String userName;
    private Integer userType = 1;

    @JsonProperty(value="userID")
    private Long userID;

    private LocalDateTime firstLoginTime;
    private LocalDateTime lastLoginTime;
    private LocalDateTime lastLogOutTime;
    private LocalDateTime lastStateUpdateTime;
    private Integer isUse;

    public final static Integer LOGIN_STATE_OFFLINE = 1;
    public final static Integer LOGIN_STATE_ONLINE = 0;

    public final static Integer STATION_IN_USE = 1;
    public final static Integer STATION_STOPPED = 0;
}
