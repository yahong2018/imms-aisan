package com.zhxh.imms.mes.mfc.domain;

import com.zhxh.imms.data.domain.Entity;
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
