package com.ezen.g15.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g15.dto.Paging;
import com.ezen.g15.dto.ProductVO;
import com.ezen.g15.service.AdminService;

@Controller
public class AdminController {

	@Autowired
	AdminService as;
	
	
	@RequestMapping("/admin")
	public String admin() {
		return "admin/adminLoginForm";			
	}
	
	@RequestMapping("adminLogin")
	public ModelAndView adminLogin( 
			HttpServletRequest request,
			@RequestParam(value="workId",required=false) String workId,
			@RequestParam(value="workPwd",required=false) String workPwd) {
		
		ModelAndView mav = new ModelAndView();
		
		if(workId == null) {
			mav.addObject("message" , "아이디를 입력하세요");
    		mav.setViewName("admin/adminLoginForm");
    		return mav;
		}else if( workId.equals("")) {
			mav.addObject("message" , "아이디를 입력하세요");
			mav.setViewName("admin/adminLoginForm");
			return mav;
		}else if(workPwd == null) {
			mav.addObject("message" , "비밀번호를 입력하세요");
			mav.setViewName("admin/adminLoginForm");
			return mav;
		}else if(workPwd.equals("")){
			mav.addObject("message" , "비밀번호를 입력하세요");
			mav.setViewName("admin/adminLoginForm");
			return mav;
		}
		int result = as.workerCheck(workId , workPwd);
		
		if(result == 1) {
			HttpSession session = request.getSession();
    		session.setAttribute("workId", workId);
    		mav.setViewName("redirect:/productList");
		}else if( result == 0) {
			mav.addObject("message" , "비밀번호를 확인하세요");
			mav.setViewName("admin/adminLoginForm");
		}else if(result == -1) {
			mav.addObject("message" , "아이디를 확인하세요");
			mav.setViewName("admin/adminLoginForm");
		}
		
		return mav;
	}
	
	
	@RequestMapping("/productList")
	public ModelAndView product_list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
	    String id = (String)session.getAttribute("workId");
	    if (id == null) 
	    	mav.setViewName("redirect:/admin");
	    else {
	    	HashMap<String,Object> result = as.getProductList(request);
	    	
	    	mav.addObject("paging", (Paging)result.get("paging"));
			mav.addObject("productList" , (List<ProductVO>)result.get("productList"));
			mav.addObject("key" , (String)result.get("key"));
			mav.setViewName("admin/product/productList");
			//Controller는 Service가 작업해서 보내준 결과들을 mav에 넣어 목적지로 이동만 함
	    	
	    }
		return mav;
	}
}



