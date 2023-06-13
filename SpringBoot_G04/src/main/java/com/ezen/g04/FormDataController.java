package com.ezen.g04;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormDataController {

	@RequestMapping("/")
	public String root() throws Exception {

		return "testForm";
	}

	@RequestMapping("/test1")
	public String test1(HttpServletRequest request, Model model) {

		String id = request.getParameter("id");
		String name = request.getParameter("name");

		model.addAttribute("id", id);
		model.addAttribute("name", name);

		return "test1";

	}

	// 매개변수에 request를 쓰지 않고 파라미터를 아래와 같이 변수에 저장할 수 있음
	@RequestMapping("/test2")
	public String test2(@RequestParam("id") String id, @RequestParam("name") String name, Model model) {

		// RequestParam 사용 특성
		// 1. 정수형 자료를 정수형 변수에 Integer.parseInt 없이 바로 저장 가능
		// 2. 전달되는 자료의 형태가 반드시 동일해야 에러 발생X
		// 3. 전달값이 없으면(null) 에러 발생
		// 4. 에러 발생 방지를 위해 @RequestParam(value = "id", required=false) String id 로 작성하기도
		// 함

		/*
		 * String id = request.getParameter("id"); String name=
		 * request.getParameter("name");
		 */

		model.addAttribute("id", id);
		model.addAttribute("name", name);

		return "test2";
	}

	@RequestMapping("/test3")
	public String test3(@ModelAttribute("mem") MemberDto memberdto) {
		// ModelAttribute를 쓰면 아무것도 안써도 MemberDto의 객체가 Model에 담겨 전달됨
		// 당연히 폼에는 같은 이름의 (id 또는 name등) 파라미터가 존재해야됨
		// ModelAttribute 없이 MemberDto만 써도 자동으로 담기지만, ModelAttribute를 쓴 이유는 담긴 내용을
		// mem(변수명)으로 전달하기 위함임

		// 파라미터와 일치하는 멤버변수가 있는 객체를 만들고 이 객체를 매개변수로 사용할 수 있음
		// 전달된 파라미터는 매개변수에 자동으로 입력됨
		// ModelAttribute 어노테이션을 통해 자동으로 Model에도 저장
		// 위 매개변수에 사용한 어노테이션은 Model.addAttribute("mem", member)라고 쓴 것과 같음
		return "test3";
	}

	@RequestMapping("/test4/{studentId}/{name}") // ex) http://localhost:8070/test4/hong/dong
	//노출되어도 상관없는 것들만 넣음 ( 주소로 공개가 되기 때문에)
	public String getStudent(@PathVariable String studentId, @PathVariable String name, Model model) {
		model.addAttribute("id", studentId);
		model.addAttribute("name", name);
		return "test4";

	}
}
