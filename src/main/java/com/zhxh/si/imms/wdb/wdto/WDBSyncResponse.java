package com.zhxh.si.imms.wdb.wdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WDBSyncResponse {
    private Long tranId;
    private String tranCode;
    private boolean status;
    private String message;
}
