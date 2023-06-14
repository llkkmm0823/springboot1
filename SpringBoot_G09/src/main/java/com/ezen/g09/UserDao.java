package com.ezen.g09;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	
	@Autowired
	private JdbcTemplate template;

	public List<UserDto> selectAll() {
	/*ArrayList<UserDto> list = new ArrayList<UserDto>();*/
		
	String sql = "select*from myuser";
	
	List<UserDto> list =template.query(sql,new BeanPropertyRowMapper<UserDto>(UserDto.class));
	// ResultSet 사용없이 검색 결과 레코드의 필드를 Dto변수에 넣고 list에 add 동작을 결과 레코드 갯수만큼 실행
	
	// BeanPropertyRowMapper를 사용하면 SQL 쿼리 결과를 자동으로 자바 객체로 변환가능
	// ResultSet의 열 이름과 자바 객체의 속성 이름을 매핑하여 자동으로 값을 설정
	// 예를 들어, "id" 열은 자바 객체의 "id" 속성에 매핑
	return list;
	}

}
