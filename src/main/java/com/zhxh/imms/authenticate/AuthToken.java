package com.zhxh.imms.authenticate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthToken {
    private String access_token;
    private String token_type;
    private AuthTokenProfile profile;
}
