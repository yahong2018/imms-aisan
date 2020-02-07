package com.zhxh.imms.si.kocheer.domain;

import com.zhxh.imms.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WorkstationSessionStep extends Entity {
    private Long workstationSessionId;
    private Integer step = WorkstationSession.SESSION_STEP_INIT;
    private LocalDateTime reqTime=LocalDateTime.now();
    private Integer reqDataType;
    private String reqData;
    private String respData;
    private LocalDateTime respTime;

    private WorkstationSession session;
}
