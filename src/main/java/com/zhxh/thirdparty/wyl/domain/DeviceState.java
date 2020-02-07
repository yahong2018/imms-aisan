package com.zhxh.thirdparty.wyl.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DeviceState {
    private Integer id;
    private Integer did;
    private Integer gid;
    private Integer state;
    private LocalDateTime updateTime;
}
