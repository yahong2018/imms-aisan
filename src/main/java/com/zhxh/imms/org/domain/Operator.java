package com.zhxh.imms.org.domain;

import com.zhxh.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Operator extends Entity {
    private String employeeId;
    private String employeeName;
    private String employeeCardNo;
    private Long orgId;
    private String orgCode;
    private String orgName;
}
