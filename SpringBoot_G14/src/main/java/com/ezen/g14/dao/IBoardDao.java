package com.ezen.g14.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g14.dto.BoardVO;
import com.ezen.g14.dto.Paging;

@Mapper
public interface IBoardDao {

	int getAllCount();
	List<BoardVO> getBoardList(Paging paging);
	int getReplyCount(int num);

	
	
}

