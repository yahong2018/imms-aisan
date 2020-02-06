package com.zhxh.kanban;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProgressKanbanFrontController {
    @RequestMapping("front/kanban/progress")
    public String index(){
        return "kanban/progress";
    }
}
