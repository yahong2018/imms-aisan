package com.zhxh.imms.mes.material.domain;

import com.zhxh.imms.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Bom extends Entity {
    private String bomNo;
    private int bomType;
    private int bomStatus;

    private Long materialId;
    private String materialCode;
    private String materialName;

    private Long componentId;
    private String componentCode;
    private String componentName;

    private int materialQty;
    private int componentQty;

    private LocalDate effectDate;
}
