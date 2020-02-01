package com.zhxh.si.imms.wdb.wdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WDBLoginParameter {
    private String grant_type;
    private String client_id;
    private String client_secret;
    private String username;
    private String password;
}
