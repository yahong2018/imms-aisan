package com.zhxh.imms.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhxh.imms.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramPrivilege extends Entity {
    private long programId;
    private String privilegeCode;
    private String privilegeName;

    @JsonIgnore
    private SystemProgram program;
}
