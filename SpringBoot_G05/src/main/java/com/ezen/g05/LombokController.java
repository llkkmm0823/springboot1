package com.ezen.g05;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LombokController {
	//리퀘스트매핑이 고전코드라는 얘기가 있음.. 참고
	@RequestMapping("/")
	public String root(){

		return "testForm";
	}

	@RequestMapping("/test1")
	public String test1(@ModelAttribute("memberdto") MemberDto memberdto, Model model) {
		
		System.out.println(memberdto.getId()+" "+memberdto.getName());
		return "test1";
		
	}
}
