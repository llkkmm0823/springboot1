package com.ezen.g17.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g17.dao.IAdminDao;

@Service
public class AdminService {

	@Autowired
	IAdminDao adao;

	public int workerCheck(String workId, String workPwd) {
		// TODO Auto-generated method stub
		return 0;
	}

	public HashMap<String, Object> getProductList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
}


















