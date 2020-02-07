package com.zhxh.imms.admin.model;

import com.zhxh.imms.admin.domain.BaseProgram;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SystemMenu extends BaseProgram {
    private List<SystemMenu> children=new ArrayList<>();
    private boolean expanded;
    private boolean leaf;
}
