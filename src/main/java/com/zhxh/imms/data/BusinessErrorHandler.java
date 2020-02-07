package com.zhxh.imms.data;

import com.zhxh.imms.utils.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;

//@ControllerAdvice
public class BusinessErrorHandler {
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView defaultExceptionHandler(BusinessException e) {
        Logger.error(e);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", e.getMessage());
        modelAndView.addObject("stackTrace",exceptionStackTrace(e));
        modelAndView.setViewName("business-error");

        return modelAndView;
    }

    private static String exceptionStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return sw.toString();
    }
}