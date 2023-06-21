package com.ezen.g15.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g15.dao.IAdminDao;

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
		return result;
	}
}
