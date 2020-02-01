package com.zhxh.si.imms.wdb.wdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialSyncItem {
    private String materialCode;
    private String materialName;
    private String description;
    private int dataType;

    public final static int DATA_TYPE_INSERT=0;
    public final static int DATA_TYPE_UPDATE = 1;
    public final static int DATA_TYPE_DELETE = 2;
}
