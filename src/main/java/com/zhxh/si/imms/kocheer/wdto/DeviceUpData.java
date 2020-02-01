package com.zhxh.si.imms.kocheer.wdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceUpData {
    private Long loginStationID;
    private Long loginUserID;
    private Integer GID;
    private Integer DID;
    private Integer isNewData;
    private String dataContent;
}
