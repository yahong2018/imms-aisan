package com.zhxh.imms.mfc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhxh.data.domain.Entity;
import com.zhxh.imms.org.domain.Workshop;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class RfidCard extends Entity {
    private String kanbanNo;
    private String rfidNo;
    private Integer cardType;
    private Integer cardStatus;
    private Long productionId;
    private String productionCode;
    private String productionName;
    private Long workshopId;
    private String workshopCode;
    private String workshopName;
    private Integer issueQty;
    private Integer stockQty = 0;
    private Integer outsourceQty = 0;
    private Long lastBusinessId = 0L;
    private String towerNo;

    public String getCardStatusName() {
        return statusNameMap.get(this.cardStatus);
    }

    @JsonIgnore
    private Workshop workshop;

    private static final Map<Integer, String> statusNameMap = new HashMap<>();

    public static final int CARD_STATUS_NOT_USE = 0;
    public static final int CARD_STATUS_ISSUED = 1;
    public static final int CARD_STATUS_BACKED = 2;
    public static final int CARD_STATUS_BINDED = 3;
    public static final int CARD_STATUS_OUTSOURCED = 4;
    public static final int CARD_STATUS_REPORTED = 10;
    public static final int CARD_STATUS_MOVED = 20;
    public static final int CARD_STATUS_EXPIRED = 255;

    public static final int CARD_TYPE_EMPLOYEE = 1;//员工卡
    public static final int CARD_TYPE_INTERNAL = 2; //内部看板
    public static final int CARD_TYPE_OUTSOURCE = 3;//外发看板

    static {
        statusNameMap.put(CARD_STATUS_NOT_USE, "0.未使用");
        statusNameMap.put(CARD_STATUS_ISSUED, "1.已发卡");
        statusNameMap.put(CARD_STATUS_BACKED, "2.已退回");
        statusNameMap.put(CARD_STATUS_BINDED, "3.已绑定");
        statusNameMap.put(CARD_STATUS_OUTSOURCED, "4.已外发");
        statusNameMap.put(CARD_STATUS_REPORTED, "10.已报工");
        statusNameMap.put(CARD_STATUS_MOVED, "20.已移库");
        statusNameMap.put(CARD_STATUS_EXPIRED, "255.已作废");
    }
}
