package com.ezen.g17.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g17.dao.ICartDao;

@Service
public class CartService {

	@Autowired
	ICartDao cdao;

	
}





