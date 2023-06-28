package com.shopmax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping(value="/") //localhost로 들어갔을 때
	public String main() {
		return "/main";	
	}
	
	
	
}
