package com.ezen.g15.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.g15.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	MemberService ms;
	
	@RequestMapping("loginForm")
	public String login_form() {
		return "member/login";
	}
	
}
