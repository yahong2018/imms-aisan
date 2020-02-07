package com.zhxh.imms.mes.material.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BomTree {
    private Long bomId;
    private Long componentId;
    private String componentCode;
    private String componentName;
    private Integer materialQty;
    private Integer componentQty;

    private List<BomTree> children;
    private boolean expanded;
    private boolean leaf;
    private boolean checked;
}
