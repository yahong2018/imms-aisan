package com.zhxh.imms.si.wdb.wdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveSyncItem {
    private String loccode;
    private String aloccode;
    private String procode;
    private String unitcode;
    private int qty;
}
