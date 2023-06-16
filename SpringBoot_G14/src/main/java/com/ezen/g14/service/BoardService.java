package com.ezen.g14.service;



import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g14.dao.IBoardDao;
import com.ezen.g14.dto.BoardVO;
import com.ezen.g14.dto.Paging;

@Service
public class BoardService {
	
	@Autowired
	IBoardDao bdao;
	 
	public HashMap<String, Object> getBoardList(HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<String, Object>();
	
	
	HttpSession session = request.getSession();
	// paging 객체 작업
	int page = 1;
	if( request.getParameter("page")!=null) {
		page = Integer.parseInt( request.getParameter("page"));
		session.setAttribute("page",page);
	}else if( session.getAttribute("page")!=null) {
		page = (Integer)session.getAttribute("page");
	}else {
		session.removeAttribute("page");
	}	
	Paging paging = new Paging();
	paging.setPage(page);
	int count = bdao.getAllCount();
	paging.setTotalCount(count);
	paging.paging(); // private에서 public으로 바뀐 paging 메서드를 수동으로 호출
	
	// 작업이 끝난 paging 객체를 이용해서 화면에 보여줄 해당 게시물 조회
	List<BoardVO> list = bdao.getBoardList( paging);
	
	//  조회된 게시물의 댓글 개수 조회
	for( BoardVO bvo : list) {
	    int cnt = bdao.getReplyCount( bvo.getNum()); //하나의 게시물의 댓글 개수 조회	
	    bvo.setReplycnt(cnt); // 게시물의 댓글 개수를 dto에 저장
	}	
	
	result.put("boardList", list);
	result.put("paging", paging);
	
	
		return result;
	}

}
