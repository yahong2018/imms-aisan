package com.zhxh.imms.mes.mfc.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class WorkDayAndShift {
    private LocalDate timeOfOriginalWork;
    private LocalDateTime timeOfOrigin;
    private Integer shiftId;

    public WorkDayAndShift(LocalDateTime timeOfOrigin) {
        this.timeOfOrigin = timeOfOrigin;
        this.parse();
    }

    //
    //每天08:30之前，20:00之后，为晚班。每天00:00 ~ 08:30之前，属于前一天的晚班，每天20:00~24:00属于当天晚班
    //
    public void parse() {
        if (this.timeOfOrigin.getHour() > 8 && this.timeOfOrigin.getHour() < 20) {
            this.shiftId = 0;
            this.timeOfOriginalWork = timeOfOrigin.toLocalDate();
        } else if (this.timeOfOrigin.getHour() == 8 && this.timeOfOrigin.getMinute() < 30) {
            this.shiftId = 1;
            this.timeOfOriginalWork = timeOfOrigin.toLocalDate().minusDays(1); //8:30
        } else {
            this.shiftId = 1;
            this.timeOfOriginalWork = timeOfOrigin.toLocalDate();
        }
    }
}
