package com.ezen.g13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

	 @RequestMapping("/")
	 public String root() {
		 return "buy_ticket";
	 }
}
