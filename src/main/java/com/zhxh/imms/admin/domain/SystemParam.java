package com.zhxh.imms.admin.domain;

import com.zhxh.imms.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemParam extends Entity {
    private String paramType;
    private String paramCode;
    private String paramDescription;
    private String paramValue;
    private RecordCreationType recordCreationType;
}
