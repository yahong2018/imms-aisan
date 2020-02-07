package com.zhxh.imms.admin.fronController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontHomeController {
    @RequestMapping("/home")
    public String Index(){
        return "index";
    }


}
