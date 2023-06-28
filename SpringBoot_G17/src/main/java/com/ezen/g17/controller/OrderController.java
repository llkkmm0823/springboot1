package com.ezen.g17.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g17.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	OrderService os;
	
	@RequestMapping(value="/orderInsert")
	public String orderInsert( HttpServletRequest request ) {
		int oseq=0;
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser 
			= (HashMap<String, Object>)session.getAttribute("loginUser");
		if( loginUser == null ) {
			return "member/login";
		}else {
			// 카트에서 장바구니 목록 조회
			// 상품목록을 orders 와  order_detail 에 저장
			// 카트에서 주문한 목록을 삭제
			// oseq 주문번호를 리턴
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", loginUser.get("ID") );
			paramMap.put("oseq", 0 );
			
			os.insertOrder( paramMap );
			oseq = Integer.parseInt( paramMap.get("oseq").toString() );
		}
		return "redirect:/orderList?oseq="+oseq;
	}
	
	@RequestMapping(value="/orderList")
	public ModelAndView orderList( HttpServletRequest request, 
			@RequestParam("oseq") int oseq) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser 
			= (HashMap<String, Object>)session.getAttribute("loginUser");
		if( loginUser == null ) {
			mav.setViewName("member/login");
		}else {	
			HashMap<String , Object> paramMap = new HashMap<String , Object>();
			paramMap.put("oseq", oseq);
			paramMap.put("ref_cursor", null);
			os.listOrderByOseq( paramMap );
			ArrayList<HashMap<String, Object>> list 
				= (ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor");
			mav.addObject("orderList", list);
			mav.addObject("totalPrice", paramMap.get("totalPrice") );
			mav.setViewName("mypage/orderList");
		}
		return mav;
	}
	
	
	@RequestMapping(value="/orderInsertOne")
	public String orderInsertOne( HttpServletRequest request,
			@RequestParam("pseq")int pseq,
			@RequestParam("quantity")int quantity) {
		int oseq = 0;
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser 
			= (HashMap<String, Object>)session.getAttribute("loginUser");
		if( loginUser == null ) {
			return "member/login";
		}else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", loginUser.get("ID") );
			paramMap.put("pseq", pseq);
			paramMap.put("quantity", quantity);
			paramMap.put("oseq", 0);  // out 변수 키값
			os.insertOrderOne( paramMap );
			oseq = Integer.parseInt( paramMap.get("oseq").toString() );
		}
		return "redirect:/orderList?oseq="+oseq;
	}
}






















