package com.zhxh.imms.admin.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeItem {
    private String old;
    private String pwd1;
    private String pwd2;
}
