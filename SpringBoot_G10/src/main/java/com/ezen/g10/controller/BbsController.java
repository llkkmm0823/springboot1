package com.ezen.g10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g10.dao.BbsDao;
import com.ezen.g10.dto.BbsDto;

@Controller
public class BbsController {

	@Autowired
	BbsDao bdao;

	@RequestMapping("/")
	public ModelAndView root() {
		ModelAndView mav = new ModelAndView();

		/*
		 * List<BbsDto> list = bdao.getList(); mav.addObject("list",list);
		 */
		mav.addObject("list", bdao.getList());

		mav.setViewName("bbslist");
		return mav;
	}

	@RequestMapping("/writeForm")
	public String writeForm() { // 단순히 이동만 할 것이기 때문에 modelandview까지는 필요없음
		return "writeForm";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	// @PostMapping("/write") 둘 중에 한 가지 방법으로 사용하면 됨

	public String write(BbsDto bbsdto) { // bbsdto만 넣어줘도 필드에 맞게 자동으로 전달됨
		// 지금 model에 넣어 전달해줄 곳은 없으므로 modelAttribute는 생략

		bdao.write(bbsdto);

		return "redirect:/";// 레코드를 추가한 뒤 redirect:/로 가면 redirect가 내용을 조회하여 보내줌
	}

	@RequestMapping("view")
	public ModelAndView view(@RequestParam("id") int id , Model model) {
		ModelAndView mav = new ModelAndView();
		//BbsDto bdto = bdao.view(id);
		//mav.addObject("dto",bdto);
		mav.addObject("dto",bdao.view(id));
		
		mav.setViewName("view");
		return mav;
	}
	
	@RequestMapping("/updateForm")
	public ModelAndView updateForm(@RequestParam("id") int id , Model model) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto",bdao.view(id));
		mav.setViewName("updateForm");
		return mav;
	}
	
	@PostMapping("/update")
	public String update(BbsDto bbsdto) {

		bdao.update(bbsdto);

		return "redirect:/";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("id") int id) {

		bdao.delete(id);

		return "redirect:/";
	}
}
