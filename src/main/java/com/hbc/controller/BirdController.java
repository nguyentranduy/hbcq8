package com.hbc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.hbc.constant.SessionConst;

import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin("*")
public class BirdController {

	@GetMapping("/bird")
	
	public String doGetBird(HttpSession httpSession) {
		if (httpSession.getAttribute(SessionConst.CURRENT_USER) == null) {
			return "page/user/login";
		}
		return "page/user/bird";
		
	}
}
