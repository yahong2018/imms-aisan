package com.zhxh.imms.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiCallResult {
    private boolean success = false;
    private String message = "";
    private Object data = null;
}
