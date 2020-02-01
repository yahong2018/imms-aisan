package com.zhxh.admin.fronController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FrontLoginController {
	@RequestMapping(value = "/front-login",method = RequestMethod.GET)
	public String index() {
		return "front-login";
	}
}
