package com.ezen.g17.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g17.dto.MemberVO;


@Mapper
public interface IMemberDao {

	void getMember(HashMap<String, Object> paramMap);

	void joinKakao(HashMap<String, Object> paramMap);

	void insertMember(MemberVO membervo);


}
