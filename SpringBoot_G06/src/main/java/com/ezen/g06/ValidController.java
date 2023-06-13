package com.ezen.g06;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ValidController {

	@RequestMapping("/")
	public String main() {
		return "startPage";
	}

	@RequestMapping("/create")
	public String create(@ModelAttribute("dto") ContentDto contentdto, Model model) {

		// 전송된 값들을 검사해서 한개의 값이라도 비어있으면 startPage.jsp 로 되돌아 감
		// 정상적인 값들이 전송되면 DonePage.jsp로 이동

		return "DonePage";
	}

}
