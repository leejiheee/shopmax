package com.shopmax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
	
	//문의하기
	@GetMapping(value="/members/qa") //폴더경로
	public String qa() {
		return "member/qa"; //주소
	}
	
	//로그인 화면
	@GetMapping(value="/members/login")
	public String loginMember() {
		return "member/memberLoginForm";
	}
	
	
	//회원가입 화면
	@GetMapping(value="/members/new")
	public String memberFrom() {
		return "member/memberForm";
	}


}
