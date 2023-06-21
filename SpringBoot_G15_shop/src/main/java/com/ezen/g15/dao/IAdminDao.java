package com.ezen.g15.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAdminDao {

	String getPwd(String workId);

	
}
