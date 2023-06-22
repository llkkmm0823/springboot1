package com.ezen.g15.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g15.dto.Paging;
import com.ezen.g15.dto.ProductVO;
import com.ezen.g15.service.AdminService;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

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
	
	@RequestMapping("/productWriteForm")
	public String product_write_form(HttpServletRequest request, Model model) {
		String kindList[] = {"Heels","Boots","Sandals","Snickers","Slipers","Sale"};
		model.addAttribute(kindList);
		return "admin/product/productWriteForm";
		
	}
	
	@Autowired
	ServletContext context;
	
	@RequestMapping(value="/fileup", method = RequestMethod.POST)
	@ResponseBody //호출했던 위치로 되돌아가는 어너테이션
	public HashMap<String,Object> fileup(Model model, HttpServletRequest request) {
		
		//현재 메서드는 다른 메서드처럼 jsp파일 이름을 리턴해서 파일이름.jsp로 이동하는 메서드가 아님
		//ajax에 의해서 호출된 지점으로 다시 되돌아가 화면이동없이 운영이 계속되어야 하기 때문에 이동할 때 가져갈 데이터 리턴 
		//따라서 리턴데이터는 여러 개가 존재할 가능성이 있으므로 해시맵에 담겨 리턴될 예정
		String path = context.getRealPath("/product_images");
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		try {
			MultipartRequest multi = new MultipartRequest(
					request, path, 5*1024*1024, "UTF-8", new DefaultFileRenamePolicy() );
			result.put("STATUS",1);
			result.put("FILENAME",multi.getFilesystemName("fileimage"));

		} catch (IOException e) {  e.printStackTrace();
		}
		return result;
		
		
		
	}
	
}



