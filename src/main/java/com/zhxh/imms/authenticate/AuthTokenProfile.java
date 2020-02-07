package com.zhxh.imms.authenticate;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuthTokenProfile {
    private String sid;
    private String name;
    private LocalDateTime auth_time;
    private LocalDateTime expires_at;
}
