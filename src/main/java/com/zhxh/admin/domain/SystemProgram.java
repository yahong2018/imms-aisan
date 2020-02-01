package com.zhxh.admin.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SystemProgram extends BaseProgram {
    private List<SystemProgram> children = new ArrayList<>();
    private List<ProgramPrivilege> privileges = new ArrayList<>();
}
