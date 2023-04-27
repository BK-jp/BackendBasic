package com.devjp.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/success")
	public String success() {
		return "success";
	}
}
