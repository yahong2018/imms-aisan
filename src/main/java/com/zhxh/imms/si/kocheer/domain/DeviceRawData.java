package com.zhxh.imms.si.kocheer.domain;

import com.zhxh.imms.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DeviceRawData extends Entity {
    private Long stationId;
    private Integer gid;
    private Integer did;
    private Boolean newData;
    private String dataContent;
    private LocalDateTime createDate;
}
