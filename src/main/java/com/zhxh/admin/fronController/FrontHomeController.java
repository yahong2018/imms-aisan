package com.zhxh.admin.fronController;

import com.zhxh.admin.domain.SystemUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontHomeController {
    @RequestMapping("/home")
    public String Index(){
        return "index";
    }


}
