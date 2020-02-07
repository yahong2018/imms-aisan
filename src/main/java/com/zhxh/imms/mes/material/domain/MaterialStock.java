package com.zhxh.imms.mes.material.domain;

import com.zhxh.imms.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialStock extends Entity {
    private Long storeId;
    private String storeCode;
    private String storeName;

    private Long materialId;
    private String materialCode;
    private String materialName;

    private int qtyStock;
    private int qtyMoveIn;
    private int qtyBackIn;
    private int qtyBackOut;
    private int qtyConsumeGood;
    private int qtyConsumeDefect;
    private int qtyGood;
    private int qtyDefect;
    private int qtyMoveOut;
}
