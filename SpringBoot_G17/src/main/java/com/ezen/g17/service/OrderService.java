package com.ezen.g17.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g17.dao.ICartDao;
import com.ezen.g17.dao.IOrderDao;

@Service
public class OrderService {

	@Autowired
	IOrderDao odao;

	public void insertOrder(HashMap<String, Object> paramMap) {
		odao.insertOrder( paramMap );		
	}

	public void listOrderByOseq(HashMap<String, Object> paramMap) {
		odao.listOrderByOseq( paramMap );
		
		ArrayList<HashMap<String, Object>> list
			=(ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor");
		int totalPrice = 0;
		for( HashMap<String , Object> ovo : list) {
			totalPrice +=	Integer.parseInt( ovo.get("QUANTITY").toString() )
									* Integer.parseInt( ovo.get("PRICE2").toString() );
		}
		paramMap.put("totalPrice", totalPrice );
	}

	public void insertOrderOne(HashMap<String, Object> paramMap) {
		odao.insertOrderOne( paramMap );			
	}

}













