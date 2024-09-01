package com.hbc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin("*")
public class ProfileController {

	@GetMapping("/profile")
	public String doGetProfile() {
		return "/page/User/profile";
	}
}
