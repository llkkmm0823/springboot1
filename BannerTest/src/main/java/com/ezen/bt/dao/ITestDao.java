package com.ezen.bt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.bt.dto.BannerVO;

@Mapper
public interface ITestDao {

	List<BannerVO> getBannerList();
	void insertBanner(BannerVO bannervo);
	List<BannerVO> getBannerListFive();
	
}
