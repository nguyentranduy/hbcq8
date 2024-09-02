package com.hbc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.hbc.constant.SessionConst;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
@CrossOrigin("*")
public class ProfileController {

	@GetMapping("/profile")
	public String doGetProfile(HttpSession httpSession) {
		if(httpSession.getAttribute(SessionConst.CURRENT_USER) == null) {
			return "page/user/index";
		}
		return "/page/User/profile";
	}
}
