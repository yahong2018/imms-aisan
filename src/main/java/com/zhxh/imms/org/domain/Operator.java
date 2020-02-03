package com.zhxh.imms.org.domain;

import com.zhxh.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Operator extends Entity {
    private String employeeId;
    private String employeeName;
    private String employeeCardNo;
    private List<Workshop> belongedWorkshops=new ArrayList<>();
}
