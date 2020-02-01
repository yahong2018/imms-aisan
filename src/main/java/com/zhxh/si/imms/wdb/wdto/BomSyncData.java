package com.zhxh.si.imms.wdb.wdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BomSyncData {
    private int size;
    private String name;

    private BomSyncItem[] values;
    private BomSyncField[] fields;
}
