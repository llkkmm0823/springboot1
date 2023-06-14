package com.ezen.g09;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	
	/* UserDao udao = new UserDao(); */
	
	@Autowired
	UserDao udao; //@Autowired에 의해 udao에는 방금 new UserDao()가 저장된거임
	
	@RequestMapping("/")
	public String main(Model model) {
	List<UserDto> list = udao.selectAll();
	model.addAttribute("users",list);
	
	return "userlist";
	}
	
	
}
