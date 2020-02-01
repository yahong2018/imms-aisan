package com.zhxh.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
public class TestController {
    @RequestMapping("/api/test")
    @ResponseBody
    public String Index(){
        return LocalDateTime.now().toLocalDate().toString();
    }
}
