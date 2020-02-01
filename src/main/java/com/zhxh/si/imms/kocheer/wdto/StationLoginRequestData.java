package com.zhxh.si.imms.kocheer.wdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StationLoginRequestData {
    private String stationCode;  //唯一编号
    private String stationIP;
    private String softWareName;
    private String softWareVersion;

    private String loginName;
    private String loginPass;
}
