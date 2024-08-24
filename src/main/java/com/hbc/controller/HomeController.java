package com.hbc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@GetMapping("/home")
    public String showIndex() {
        // Trả về đường dẫn tới template mà không cần thêm phần mở rộng .html
        return "page/User/index";
    }
	 @GetMapping("/login")
	    public String showLogin() {
	        return "page/User/login"; // Trang login
	    }
}
