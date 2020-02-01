package com.zhxh.imms.mfc.domain;

import com.zhxh.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductSummary extends Entity {
    private LocalDate productDate;
    private Long workshopId;
    private String workshopCode;
    private String workshopName;
    private Long productionId;
    private String productionCode;
    private String productionName;
    private Integer qtyGood0;
    private Integer qtyDefect0;
    private Integer qtyGood1;
    private Integer qtyDefect1;
}
