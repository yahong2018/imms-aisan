package com.zhxh.imms.web;

import com.zhxh.imms.data.BusinessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BusinessExceptionHandler {
//    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
//    @ResponseBody
//    public Map<String,Object> handle(BusinessException e){
//        Map<String,Object> result = new HashMap<>();
//        result.put("data",e);
//        result.put("success",false);
//
//        return result;
//
//    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public BusinessException handle(BusinessException e) {
        return e;
    }
}
