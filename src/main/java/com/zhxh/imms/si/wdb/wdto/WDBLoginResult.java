package com.zhxh.imms.si.wdb.wdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WDBLoginResult {
    private String access_token;
    private String refresh_token;
    private int uid;
    private String token_type;
}
