package com.ezen.bt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.bt.dao.ITestDao;
import com.ezen.bt.dto.BannerVO;

@Service
public class TestService {

	@Autowired
	ITestDao tdao;

	public List<BannerVO> getBannerList() {
		//return tdao.getBannerList();
		return null;
	}

	public void insertBanner(BannerVO bannervo) {
		//tdao.insertBanner();
	}

	public List<BannerVO> getBannerListFive() {
		//return tdao.getBannerListFive();
		return null;
	}
}
