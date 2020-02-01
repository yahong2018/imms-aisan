package com.zhxh.imms.mfc.domain;

import com.zhxh.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WorkstationBind extends Entity {
    private Long outsourceCardId;
    private Long workstationId;
    private Long workshopId;
    private LocalDateTime attachTime;
    private Integer bindStatus;  //3.已绑定   4.已外发

    public final static int BIND_STATUS_BIND = RfidCard.CARD_STATUS_BINDED;
    public final static int BIND_STATUS_OUT = RfidCard.CARD_STATUS_OUTSOURCED;
}
