package com.zhxh.imms.mfc.domain;

import com.zhxh.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CardBind extends Entity {
    private Long outsourceCardId;
    private Long qtyReportId;
    private Long qtyCardId;
    private LocalDateTime attachTime;
    private Long workstationBindId;
}
