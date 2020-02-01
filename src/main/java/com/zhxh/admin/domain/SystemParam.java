package com.zhxh.admin.domain;

import com.zhxh.data.domain.Entity;
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
