package com.zhxh.si.imms.wdb.wdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GwInstoreItem {
    private String kanbanNo;
    private String productionCode;
    private String productionName;
    private String storeNo;
    private String storeName;
    private String operatorCode;
    private String operatorName;
    private int qty;
    private String movingTime;
}
