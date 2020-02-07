package com.zhxh.imms.si.wdb.wdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BomSyncItem {
    private String bomCode;
    private String proCode;
    private String proDesc;
    private String matCode;
    private String matDesc;
    private float aQty;
    private String tDate;
}
