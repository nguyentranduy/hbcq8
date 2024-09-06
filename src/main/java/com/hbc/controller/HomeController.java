package com.hbc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String doGetIndex() {
		return "page/user/index";
	}

	@GetMapping("/login")
	public String doGetLogin() {
		return "page/User/login";
	}

	@GetMapping("/register")
	public String doGetRegister() {
		return "page/User/register";
	}
}
