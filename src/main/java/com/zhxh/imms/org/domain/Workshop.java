package com.zhxh.imms.org.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhxh.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Workshop extends Entity {
    private String workshopCode;
    private String workshopName;
    private Integer workshopType;
    private String description;
    private Integer opIndex;
    private Integer prevIndex;

    @JsonIgnore
    private List<Workstation> workstations = new ArrayList<>();

    public static final int WORKSHOP_INTERNAL = 1; //内部车间
    public static final int WORKSHOP_QJG = 3; //前加工
    public static final int WORKSHOP_OUTSOURCE = 4;//外发
    public static final int WORKSHOP_HJG = 5;//后加工

    public static final String WORKSHOP_GW = "GW_STORE";
}
