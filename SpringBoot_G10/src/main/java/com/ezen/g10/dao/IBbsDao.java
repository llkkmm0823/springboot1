package com.ezen.g10.dao;

import java.util.List;

import com.ezen.g10.dto.BbsDto;

public interface IBbsDao {
	
	public List<BbsDto> getList(); 	//게시물 전체 조회 - 매개변수 없고 리턴값은 List<BbsDto>형
	public int write(BbsDto bdto); 	// 게시물 쓰기 - 매개변수 BbsDto 형, 리턴 int형
	public int update(BbsDto bdto); // 수정 - 매개변수 BbsDto형, 리턴 int형
	public int delete(int id); 		// 삭제 - 매개변수 int, 리턴 int형
	public BbsDto view (int id); 	// 게시물 하나 보기 - 매개변수 int, 리턴 BbsDto형

}
