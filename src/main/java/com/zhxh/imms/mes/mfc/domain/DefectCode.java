package com.zhxh.imms.mes.mfc.domain;

import com.zhxh.imms.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefectCode extends Entity {
    private String defectCode;
    private String defectName;
}
