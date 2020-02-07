package com.zhxh.imms.admin.model;

import com.zhxh.imms.admin.domain.ProgramPrivilege;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrivilegeAuthorizeModel extends ProgramPrivilege {
    private  boolean checked;
    private String dataType;
}
