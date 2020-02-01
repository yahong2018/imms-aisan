package com.zhxh.admin.model;

import com.zhxh.admin.domain.BaseProgram;
import com.zhxh.admin.domain.SystemProgram;
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
