package com.ezen.g16.comtroller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
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
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("dto") @Valid MemberVO membervo, BindingResult result, Model model,
			HttpServletRequest request) {
		
		String url = "member/login";
		
		if (result.getFieldError("id") != null)
			model.addAttribute("message", result.getFieldError("id").getDefaultMessage());
		else if (result.getFieldError("pwd") != null)
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
		else {

			//userid를 보내 MemberVO를 리턴
			//userid는 in변수에 보내고 "MemberVO에 해당하는 변수"를 out변수로 보냄
			//프로시져에서 select의 결과는 cursor이므로 out변수에 비어있는 cursor변수를 넣어야 하는 상황
			//현재 위치에서 PL/SQL의 커서 변수를 생성하는 것은 불가능하나 Object변수는 가능
			//전송하는 값, 전달받는 값 모두를 수용할 수 있는 HashMap사용
			HashMap<String, Object> paramMap =  new HashMap<String, Object>();
			
			paramMap.put("userid",membervo.getUserid()); //해쉬맵에 검색에 필요한 아이디 넣기
			paramMap.put("ref_cursor",null); //out 변수로 비어있는 자료형 전송
			//HashMap에 in변수, out변수 모두 담김
			
			//getMember메서드는 리턴값도 paramMap에 담겨 되돌아 올 것이므로 별도 리턴형 메서드로 만들지 않음
			ms.getMember(paramMap);

			
			//프로시져를 실행하고 돌아온 결과 : paramMap의 키값 ref_cursor자리에 ArrayList<HashMap<String,Object>>가 담겨옴
			// HashMap<String,Object> 안에는 레코드의 필드명을 키값으로 한 필드값들이 들어있음
			
			//paramMap에서 ArrayList<HashMap<String,Object>>를 꺼냄
			ArrayList<HashMap<String,Object>> list = 
					(ArrayList<HashMap<String,Object>>)paramMap.get("ref_cursor");
		}
		return url;
	}
}


















