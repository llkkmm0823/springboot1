package com.ezen.g15.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ezen.g15.dto.MemberVO;
import com.ezen.g15.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	MemberService ms;
	
	@RequestMapping("loginForm")
	public String login_form() {
	return "member/login";
	}
	
	
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(
			@ModelAttribute("dto") @Valid MemberVO membervo, BindingResult result, Model model, HttpServletRequest request) {
		String url = "member/login";
		if(result.getFieldError("id")!=null)
			model.addAttribute("message",result.getFieldError("id").getDefaultMessage());
		else if(result.getFieldError("pwd")!=null)
			model.addAttribute("message",result.getFieldError("pwd").getDefaultMessage());
		else {
			
			MemberVO mvo= ms.getMember(membervo.getId());
			if(mvo==null)model.addAttribute("message","ID가 없습니다");
				else if(mvo.getPwd()==null)model.addAttribute("message","관리자에게 문의하세요");
				else if(!mvo.getPwd().equals(mvo.getPwd()))model.addAttribute("message","암호가 잘못되었습니다");
				else if(mvo.getPwd().equals(mvo.getPwd())) {
					HttpSession session = request.getSession();
					session.setAttribute("loginUser", mvo);
			
			url="redirect:/";
		}
	}
		return url;	
	}
	
	@RequestMapping("/logout")
	public String logout(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("loginUser");
		return "redirect:/";
	}
}
