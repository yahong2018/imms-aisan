package com.zhxh.imms.mes.material.domain;

import com.zhxh.imms.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Material extends Entity {
    private String materialCode;
    private String materialName;
    private String description;
    private boolean autoFinishedProgress;
}
