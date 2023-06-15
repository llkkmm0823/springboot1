package com.ezen.g14.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g14.dao.IBoardDao;

@Service
public class BoardService {
	
	@Autowired
	IBoardDao bdao;

}
