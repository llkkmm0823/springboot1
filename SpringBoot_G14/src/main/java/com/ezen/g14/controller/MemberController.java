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
	//@Autowired MemberService ms;: MemberService 클래스의 인스턴스를 자동으로 주입합니다. 
	//이를 통해 ms 변수는 MemberService의 객체를 참조하게 됩니다.


	
	@RequestMapping("/")
	// @RequestMapping("/"): 루트 경로 ("/")에 대한 요청을 처리하는 메서드로  애플리케이션의 기본 주소에 접속했을 때 실행됨.
	public String root() {
		return "member/loginForm";
	} 
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	///login" 경로로 POST 요청이 오면 이 메서드가 실행되도록 매핑합니다.
	public String login( @ModelAttribute("dto")
	                    // MemberVO 객체를 "dto"라는 이름으로 모델에 추가합니다.
	                    @Valid MemberVO membervo, 
	                    // @Valid:데이터 유효성 검사를 수행함.
			            BindingResult result,
			            // BindingResult result: 유효성 검사 결과를 저장하는 객체
			            HttpServletRequest request, 
			            // HttpServletRequest request: HTTP 요청 정보를 담고 있는 객체
			            Model model) { 
			            // Model model: 뷰에 데이터를 전달하기 위한 모델 객체.
		
		String url = "member/loginForm";  
		// 초기 URL 값을 "member/loginForm"으로 설정합니다.
		
		if(result.getFieldError("userid") != null) {
			model.addAttribute("message", result.getFieldError("userid").getDefaultMessage());		
			// 만약 아이디 필드에 오류가 있는 경우, 해당 오류 메시지를 모델에 추가합니다.	
		}else if (result.getFieldError("pwd") != null) {
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());	  
			// 만약 비밀번호 필드에 오류가 있는 경우, 해당 오류 메시지를 모델에 추가합니다.					
	    }else { 
		   // 아이디 비번이 정상 입력된 경우
	    	MemberVO mvo = ms.getMember( membervo.getUserid()); 
	    	// 입력된 아이디로 회원 정보를 조회합니다.
	    	if ( mvo == null) model.addAttribute("message","아이디가 없으므니다");
	    	//  회원 정보가 없는 경우, "아이디가 없습니다"라는 오류 메시지를 모델에 추가합니다.	
	    	
	    	else if ( mvo.getPwd() == null)	model.addAttribute("message","DB 오류. 관리자에게 문의하시므니다");
	    	// 회원 정보가 있지만 비밀번호가 없는 경우, "DB 오류. 관리자에게 문의하세요"라는 오류 메시지를 모델에 추가합니다.	
	    	
	    	else if ( !mvo.getPwd().equals(membervo.getPwd()))
	    		model.addAttribute("message","비밀번호가 맞지않스므니다");
	    		
	        else if ( mvo.getPwd().equals( membervo.getPwd())) {
	        	// 회원 정보가 있고 비밀번호가 일치하는 경우:
	        	HttpSession session = request.getSession();
	        	// HttpServletRequest 객체를 사용하여 세션을 가져옴.
	        	session.setAttribute("loginUser", mvo);  
	        	// 세션에 로그인 사용자 정보를 저장합니다.
	        	url  = "redirect:/main";  
	        	//리다이렉트할 URL을 "redirect:/main"으로 설정.
	    	   
	       }
	    }
		return url;  
		// 최종적으로 결정된 URL을 반환하여 로그인 처리 이후에 어떤 페이지로 이동할지 결정함.
		
	}
}
