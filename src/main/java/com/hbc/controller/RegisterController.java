package com.hbc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

	@GetMapping("/register")
	public String doGetRegister() {
		return "page/User/register";
	}
}
