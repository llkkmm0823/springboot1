package com.ezen.g15.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g15.dao.IAdminDao;
import com.ezen.g15.dto.Paging;
import com.ezen.g15.dto.ProductVO;

@Service
public class AdminService {

	@Autowired
	IAdminDao adao;

	public int workerCheck(String workId, String workPwd) {
		
		String pwd = adao.getPwd(workId);
		
		int result=0;
		
		if(pwd==null) result = -1; //아이디가 없음
		else if(workPwd.equals(pwd)) result=1;//정상로그인
		else if(!workPwd.equals(pwd)) result=0; //비번틀림

		return result;
	}

	public HashMap<String, Object> getProductList(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		
		//다른 메뉴에서 현재 메뉴로 이동했을 때, 세션에 저장된 page와 key값을 모두 삭제
		if(request.getParameter("first")!=null) {
			session.removeAttribute("page");
			session.removeAttribute("key");
		}
		
		//현재 페이지 설정
		//파라미터로 전달된 값의 유무에 따라 이동할 페이지를 결정
		//파라미터로 전달된 페이지가 있다면 그 페이지로, 없다면 세션에 저장된 페이지로, 세션에 없다면 1페이지로 이동
		int page=1;
		if(request.getParameter("page")!=null) {
			page=Integer.parseInt(request.getParameter("page"));
			session.setAttribute("page", page);
		}else if(session.getAttribute("page")!=null){
			page=(int)session.getAttribute("page");
		}else {
			page=1;
			session.removeAttribute("page");
			
		}
		
		//페이지와 비슷한 방식으로 검색어(key)도 설정
		
		String key="";
		
		if(request.getParameter("key")!=null) {
			key=request.getParameter("key");
			session.setAttribute("key", key);
		}else if(session.getAttribute("key")!=null){
			key=(String)session.getAttribute("key");
		}else {
			session.removeAttribute("key");
			key="";
		}
		
		//Paging 객체 설정
		Paging paging = new Paging();
		paging.setPage(page);
		
		int count = adao.getAllCount("product"  , "name" , key);
		paging.setTotalCount( count );
		paging.paging();
		
		List<ProductVO> productList = adao.listProduct(paging , key);
		
		result.put("productList", productList);
		result.put("paging", paging);
		result.put("key", key);

		
		return result;
	}
}
