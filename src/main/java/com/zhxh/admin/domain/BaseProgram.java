package com.zhxh.admin.domain;

import com.zhxh.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseProgram extends Entity {
    private String programCode;
    private String programName;
    private String url;
    private String glyph;
    private int showOrder;
    private String parameters;
    private long parentId;
    private StartupStatus programStatus;
}
