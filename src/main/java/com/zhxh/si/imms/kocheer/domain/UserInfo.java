package com.zhxh.si.imms.kocheer.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private Long userID;
    private String loginName;
    private String loginPass;
    private String userName;
    private Integer userType;
    private String remark;
}
