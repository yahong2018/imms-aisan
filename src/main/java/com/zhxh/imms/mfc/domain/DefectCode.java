package com.zhxh.imms.mfc.domain;

import com.zhxh.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefectCode extends Entity {
    private String defectCode;
    private String defectName;
}
