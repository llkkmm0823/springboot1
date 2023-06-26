package com.ezen.g16.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IBoardDao {

	void getAllCount(HashMap<String, Object> paramMap);
	void selectBoard(HashMap<String, Object> paramMap);

}
