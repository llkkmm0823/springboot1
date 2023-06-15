package com.ezen.g14.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ezen.g14.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	BoardService bs;

}
