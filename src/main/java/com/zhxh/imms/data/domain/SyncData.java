package com.zhxh.imms.data.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SyncData extends Entity {
    private String classType;
    private Long businessId;
    private LocalDateTime syncTime;
}
