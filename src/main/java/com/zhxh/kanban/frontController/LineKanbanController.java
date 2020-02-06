package com.zhxh.kanban.frontController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LineKanbanController {
    @RequestMapping("/websocket-test")
    public String index(){
        return "websocket-test";
    }
}
