package com.zhxh.imms.si.wdb.wdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QualitySyncData {
    private int beId;
    private int doctypeId;
    private QualitySyncItem[] prodpwt;
}
