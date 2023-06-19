package com.ezen.g14.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g14.dto.BoardVO;
import com.ezen.g14.dto.Paging;
import com.ezen.g14.service.BoardService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@Controller
public class BoardController {

	@Autowired
	BoardService bs;

	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		if (session.getAttribute("loginUser") == null)
			mav.setViewName("member/loginForm");
		else {
			// page 파라미터를 품고 있는 request service 에 보내서 페이지 처리 및 해당 게시물 조회후
			// HashMap 에 모두 담아 리턴받을 예정입니다.
			HashMap<String, Object> result = bs.getBoardList(request);
			// List<BoardVO> list = ( List<BoardVO> )result.get("boardList");
			// Paging paging = (Paging) result.get("paging");
			mav.addObject("boardList", (List<BoardVO>) result.get("boardList"));
			mav.addObject("paging", (Paging) result.get("paging"));
			mav.setViewName("board/main");
		}
		return mav;
	}

	@RequestMapping("/boardView")
	public ModelAndView boardView(@RequestParam("num") int num) {
		ModelAndView mav = new ModelAndView();

		HashMap<String, Object> result = bs.boardView(num);
		mav.addObject("board", result.get("board"));
		mav.addObject("replyList", result.get("replyList"));

		mav.setViewName("board/boardView");
		return mav;
	}

	@RequestMapping("/boardWriteForm")
	public String write_form(HttpServletRequest request) {

		String url = "board/boardWriteForm";
		HttpSession session = request.getSession();
		if (session.getAttribute("loginUser") == null)
			url = "member/loginForm";

		return url;
	}

	@Autowired
	ServletContext context;

	/*@RequestMapping(value = "/boardWrite", method = RequestMethod.POST)
	public String boardWrite(HttpServletRequest request) {
		
	HttpSession session = request.getSession(); ServletContext context =
	session.getServletContext(); String path=context.getRealPath("upload");

		String path = context.getRealPath("/upload");
		BoardVO bvo = new BoardVO();
		try {
			MultipartRequest multi = new MultipartRequest(request, path, 5 * 1024 * 1024, "UTF-8",
					new DefaultFileRenamePolicy());
			bvo.setUserid(multi.getParameter("userid"));
			bvo.setPass(multi.getParameter("pass"));
			bvo.setTitle(multi.getParameter("title"));
			bvo.setEmail(multi.getParameter("email"));
			bvo.setContent(multi.getParameter("content"));
			if (multi.getFilesystemName("imgfilename") == null)
				bvo.setImgfilename("");
			else
				bvo.setImgfilename(multi.getFilesystemName("imgfilename"));
			bs.insertBoard(bvo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/main";
	}
*/
	
	@RequestMapping(value = "/boardWrite", method = RequestMethod.POST)
	public String boardWrite(@ModelAttribute("dto") @Valid BoardVO boardvo , BindingResult result, Model model) {
		
		String url = "board/boardWriteForm";
		if(result.getFieldError("pass")!=null)
			model.addAttribute("message",result.getFieldError("pass").getDefaultMessage());
		else if(result.getFieldError("title")!=null)
			model.addAttribute("message",result.getFieldError("title").getDefaultMessage());
		else if(result.getFieldError("content")!=null)
			model.addAttribute("message",result.getFieldError("content").getDefaultMessage());
		else {
			if(boardvo.getImgfilename()==null)boardvo.setImgfilename("");
			bs.insertBoard(boardvo);
			url="redirect:/main";
			}
		
		
		return url;
	}
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/selectimg")
	public String selectimg() {
		
		
		
		return "board/selectimg";
	}
	
	
	
	
	
	
	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	public String fileupload(HttpServletRequest request , Model model) {
		
		String path = context.getRealPath("/upload");
		try {
			MultipartRequest multi = new MultipartRequest(request, path, 5 * 1024 * 1024, "UTF-8",
					new DefaultFileRenamePolicy());
			//전송된 파일은 업로드되고 파일이름은 모델에 저장
			model.addAttribute("image",multi.getFilesystemName("image"));
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return "board/completupload";
		
		
	}
	
	
	
	
	
	
	
	
}








