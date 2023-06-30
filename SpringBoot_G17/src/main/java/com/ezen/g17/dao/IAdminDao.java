package com.ezen.g17.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAdminDao {

	void getAdmin(HashMap<String, Object> paramMap);
	void adminGetAllCount(HashMap<String, Object> cntMap);
	void getProductList(HashMap<String, Object> paramMap);
	void insertProduct(HashMap<String, Object> paramMap);
	void updateProduct(HashMap<String, Object> paramMap);
	void getMemberList(HashMap<String, Object> paramMap);
	void memberReinsert(HashMap<String, Object> paramMap);
	void getOrderList(HashMap<String, Object> paramMap);
	void updateOrderResult(HashMap<String, Object> paramMap);
	void getQnaList(HashMap<String, Object> paramMap);
	void updateOna(HashMap<String, Object> paramMap);
	void getBannerList(HashMap<String, Object> paramMap);
	void insertBanner(HashMap<String, Object> paramMap);
	void updateSeq(HashMap<String, Object> paramMap);

	
}
