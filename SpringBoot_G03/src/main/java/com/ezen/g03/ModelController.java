package com.ezen.g03;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ModelController {
   
	
	
	@RequestMapping("/")   // 요청 주소가  http://localhost:8070/ 일 때 실행되는 메서드
	public @ResponseBody String root() {
		return "<h1>Model & View</h1>";  // 화면에 보여질 내용이 String 으로 리턴됨
	}
	
	
	
	@RequestMapping("/test1")    // 요청 주소가  http://localhost:8070/test1 일 때 실행되는 메서드
	public String test1(HttpServletRequest request, Model model) {
		//RequestMapping메서드의 매개변수로 HttpServletRequest를 지정하면, Spring이 전달해준 request 사용 가능
		request.setAttribute("name1","홍길동");	
		//request를 계속 쓰기엔 부담스러움
		//request안에는 세션값 등 여러가지가 들어있기 때문에.
		
		//자료 전송용 객체가 따로 있으면 좋을듯  >> model객체
		//리턴되는 jsp파일까지만 해당내용을 전달 할 수 있는 1회용 자료 전달 도구
		model.addAttribute("name2","김하나");
		
		return "test1"; // 화면에 표시될 jsp 파일 이름이 리턴됨.
		//return 동작이나 이전에 별도의 request 전송 명령이 없어서 jsp파일에 request가 전달됨
	}
	
	
	
}
