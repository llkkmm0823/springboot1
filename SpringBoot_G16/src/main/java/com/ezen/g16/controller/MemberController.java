package com.ezen.g16.controller;

import java.util.ArrayList;
import java.util.HashMap;

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

import com.ezen.g16.dto.MemberVO;
import com.ezen.g16.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	MemberService ms;
	
	@RequestMapping("/")
	public String root() {
		return "member/loginForm";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(   @ModelAttribute("dto") @Valid MemberVO membervo , 
			BindingResult result,	HttpServletRequest request,		Model model ) {
		
		String url = "member/loginForm";
		
		if( result.getFieldError("userid") != null ) 
			model.addAttribute("message", result.getFieldError("userid").getDefaultMessage() );
		else if( result.getFieldError("pwd")!=null) 
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage() );
		else {
		
			// userid 를 보내서  MemberVO 를 리턴
			// userid는 IN 변수에보내고,  "MemberVO 에 해당하는 변수"를  OUT변수로 보냅니다
			
			// 그러나 프로시져에서 select의 결과는 cursor 이므로  OUT 변수에 비어 있는 cursor변수를 넣어야 하는 상황입니다.
			// 현재 위치에서  PL/SQL 의 커서변수를 만들수는 없습니다. 다만 그를 담을수 있는 Object변수는 가능합니다.
			// 그래서 보내는 값 받는 변수 모두를 수용할수 있는 HashMap 을 사용합니다
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put( "userid", membervo.getUserid() ); // 해쉬맵에 검색에 필요한 아이디를 넣어줍니다
			paramMap.put( "ref_cursor", null ); //OUT 변수로 비어 있는 자료형을 전송
			// 이로써 HashMap 에는 IN 변수, OUT 변수가 모두 담긴 셈입니다
			
			// getMember 메서드는 리턴값도 paramMap에 담겨서 되올것이기 때문에 별도 리턴형 메서드로 만들지 않습니다.
			ms.getMember( paramMap );	
			
			// 프로시져를 실행하고 돌아온결과 : paramMap 의 키값ref_cursor 자리에   ArrayList< HashMap<String, Object> > 가
			// 담겨져 옵니다    HashMap<String, Object> 안에는  레코드의 필드명을 키값으로 한 필드값들이 들어있습니다
			
			// paramMap에서 ArrayList< HashMap<String, Object> > 를 꺼냅니다
			ArrayList< HashMap<String, Object> > list 
						= (ArrayList< HashMap<String, Object> >) paramMap.get("ref_cursor" );
			// 프로시져의 결과:레코드의 리스트, 각 레코드:<필드명, 필드값> 형태의 해쉬맵입니다.
			// ArrayList 안에 담겨져 있는   HashMap<String, Object> 는  <필드명, 필드값> 입니다
			// 지금은 아이디로 검색한 결과 한개이지만 보통 여러개가 저장되어 옵니다.
			
			// 해쉬맵으로 받아오는 루틴의 특성상 키값이 모두 "대문자"입니다. 그래서 jsp에서 사용할때도 모두 대문자를 사용해야 합니다
			
			// * 리스트의 결과가 아무것도 없는지를 먼저 조사합니다.
			if( list.size() == 0 ) {
				model.addAttribute("message", "아이디가 없습니다");
				return "member/loginForm";
			}
			
			// 조회결과가 있다면 리스트에 담겨 있는 해쉬맵의 첫번째를 꺼냅니다
			HashMap<String , Object > mvo = list.get(0);
			
			if( mvo.get("PWD") == null )
				model.addAttribute("message", "비밀번호 오류. 관리자에게 문의하세요");
			else if( !mvo.get("PWD").equals( membervo.getPwd() ) )
				model.addAttribute("message", "비밀번호가 맞지않습니다");
			else if( mvo.get("PWD").equals( membervo.getPwd() ) ) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", mvo );
				url =  "redirect:/main";
			}			
		}
		return url;
	}
}


















