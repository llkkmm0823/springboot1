package com.ezen.g09;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	
	@Autowired
	private JdbcTemplate template;

	public ArrayList<UserDto> selectAll() {
	ArrayList<UserDto> list = new ArrayList<UserDto>();
		
	return list;
	}

}
