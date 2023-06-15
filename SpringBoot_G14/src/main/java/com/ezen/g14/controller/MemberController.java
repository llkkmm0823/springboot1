package com.ezen.g14.controller;

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

import com.ezen.g14.dto.MemberVO;
import com.ezen.g14.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	MemberService ms;
	
	@RequestMapping("/")
	public String root() {
		return "member/loginForm";
	}
	
	@RequestMapping(value="/login" , method=RequestMethod.POST)
	public String login(@ModelAttribute("dto") @Valid MemberVO membervo ,//넣지않아도 알아서 넣어주는 valid
						BindingResult result , 
						HttpServletRequest request,
						Model model) {
		
		String url="member/loginForm";
		
		if(result.getFieldError("userid")!= null) { 
			// result.hasErrors()
			// has error을 쓰면 valid가 작동과 동시에 memberVO에 있는 
			//not null과 not empty가 붙어있는 모든것을 검사하여 비어있으면 다 에러처리함
			//그래서 모두 error에서 다 걸려버림. 이럴 경우엔 result.hasError을 쓰지 않고 getFieldError 사용
			model.addAttribute("message",result.getFieldError("userid").getDefaultMessage());
		}else if(result.getFieldError("pwd")!= null){
			model.addAttribute("message",result.getFieldError("pwd").getDefaultMessage());
		}else {
			//아이디 비번이 정상 입력된 경우에만 else까지 넘어옴
			MemberVO mvo = ms.getMember(membervo.getUserid());
			if(mvo==null)
				model.addAttribute("message","아이디가 없습니다");
			else if(mvo.getPwd()==null)
				model.addAttribute("message","DB오류. 관리자에 문의하세요");
			else if(!mvo.getPwd().equals(mvo.getPwd()))
				model.addAttribute("message","비밀번호가 맞지 않습니다");
			else if(mvo.getPwd().equals(membervo.getPwd())) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", mvo);
				//url="main";	
				url="redirect:/main";
			}
		}			
		return url;
	}

}
