package com.ezen.g17.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import com.ezen.g17.dto.Paging;
import com.ezen.g17.service.AdminService;
import com.ezen.g17.service.ProductService;
import com.ezen.g17.service.QnaService;

@Controller
public class AdminController {

	@Autowired
	AdminService as;
	
	@RequestMapping(value="/admin")
	public String admin() {
		return "admin/adminLoginForm";
	}
	
	@RequestMapping(value="/adminLogin")
	public String adminLogin( HttpServletRequest request, Model model, 
			@RequestParam("workId") String workId, 
			@RequestParam("workPwd") String workPwd) {	
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("workId", workId);
		paramMap.put( "ref_cursor", null );
		as.getAdmin(paramMap);	 // 아이디로 관리자 조회
		ArrayList< HashMap<String,Object> > list 
			= (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
		String url = "admin/adminLoginForm";
		if(list.size() == 0) {  // 입력한 아이디 없다면
			model.addAttribute("message" , "아이디가 없어요");
			return "admin/adminLoginForm";
		}
		HashMap<String, Object> resultMap = list.get(0); 
		if(resultMap.get("PWD")==null) model.addAttribute("message" , "관리자에게 문의하세요");
		else if( !workPwd.equals( (String) resultMap.get("PWD") ) )
			model.addAttribute("message" , "비번이 안맞아요");
		else if( workPwd.equals( (String) resultMap.get("PWD") ) ) { 
			HttpSession session = request.getSession();
			session.setAttribute("loginAdmin", resultMap);
			url = "redirect:/productList";
		}
		return url;
	}
	
	
	@RequestMapping(value="/adminLogout")
	public String adminLogout( HttpServletRequest request ) {
		
		HttpSession session = request.getSession();
		session.removeAttribute("loginAdmin" );
		
		return "admin/adminLoginForm";
	}
	
	
	
	@RequestMapping(value="/productList")
	public ModelAndView product_list(HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		if( session.getAttribute("loginAdmin")==null) 
			mav.setViewName("admin/adminLoginForm");
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("request", request);
			paramMap.put( "ref_cursor", null );
			as.getProductList( paramMap );
			
			ArrayList< HashMap<String,Object> > list 
				= (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
			
			mav.addObject("productList", list);
			mav.addObject("paging", paramMap.get("paging") );
			mav.addObject("key", paramMap.get("key") );
			mav.setViewName("admin/product/productList");
		}
		return mav;
	}
	
	
	
	@RequestMapping(value="/productWriteForm")
	public String product_write_form( HttpServletRequest request, Model model) {
		String kindList[] = { "Heels", "Boots", "Sandals", "Snickers", "Slipers",  "Sale" };
		model.addAttribute("kindList", kindList);
		return "admin/product/productWriteForm";
	}
	
	
	@Autowired
	ServletContext context;
	
	@RequestMapping(value="fileup", method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> fileup( 
			@RequestParam("fileimage") MultipartFile file,
			HttpServletRequest request, Model model	) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String path = context.getRealPath("/product_images");	
		Calendar today = Calendar.getInstance();
 		long t = today.getTimeInMillis();
 		String filename = file.getOriginalFilename(); // 파일이름 추출
 		String fn1 = filename.substring(0, filename.indexOf(".") );  // 파일이름과 확장장 분리
 		String fn2 = filename.substring(filename.indexOf(".")+1 );
 		
 		if (!file.isEmpty()) {   // 업로드할 파일이 존재한다면
            String uploadPath = path + "/" + fn1 + t +  "." + fn2;
            System.out.println("파일 저장 경로 = " + uploadPath);
            try {
				file.transferTo( new File(uploadPath) );
			} catch (IllegalStateException e) { e.printStackTrace();
			} catch (IOException e) { e.printStackTrace();
			}
 		}
		result.put("STATUS", 1);
		result.put("FILENAME", fn1 + t +  "." + fn2 );
		return result;
	}
	
	@RequestMapping(value="/productWrite" , method = RequestMethod.POST)
	public String productWrite(	Model model ,  HttpServletRequest request ) {
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", request.getParameter("name") );
		paramMap.put("kind", request.getParameter("kind") );
		paramMap.put("price1", Integer.parseInt( request.getParameter("price1") ) );
		paramMap.put("price2", Integer.parseInt( request.getParameter("price2") ) );
		paramMap.put("price3", Integer.parseInt( request.getParameter("price3") ) );
		paramMap.put("content", request.getParameter("content") );
		if( request.getParameter("image") == null )
			paramMap.put("image", "" );
		else 
			paramMap.put("image", request.getParameter("image") );
		as.insertProduct( paramMap);
		return "redirect:/productList";
	}
	
	@Autowired
	ProductService ps;
	
	@RequestMapping("/adminProductDetail")
	public ModelAndView adminProductDetail( HttpServletRequest request,
			@RequestParam("pseq") int pseq ) {
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		if( session.getAttribute("loginAdmin")==null) 
			mav.setViewName("admin/adminLoginForm");
		else {
			HashMap<String , Object>paramMap = new HashMap<String, Object>();
			paramMap.put("pseq", pseq);
			paramMap.put("ref_cursor", null);
			ps.getProduct(paramMap);
			ArrayList< HashMap<String , Object> > list
				=(ArrayList< HashMap<String , Object> >) paramMap.get("ref_cursor");
			HashMap<String , Object> pvo = list.get(0);
			String kindList[] = { "0", "Heels", "Boots", "Sandals", "Snickers", "Slipers",  "Sale" };
			mav.addObject("kind", kindList[ Integer.parseInt( pvo.get("KIND").toString() ) ] );
			mav.addObject("productVO", pvo);
			mav.setViewName("admin/product/productDetail");
		}
		return mav;
	}
	
	@RequestMapping("productUpdateForm")
	public ModelAndView productUpdateForm( HttpServletRequest request,
			@RequestParam("pseq") int pseq ) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		
		if( session.getAttribute("loginAdmin")==null) 
			mav.setViewName("admin/adminLoginForm");
		else {
			HashMap<String , Object>paramMap = new HashMap<String, Object>();
			paramMap.put("pseq", pseq);
			paramMap.put("ref_cursor", null);
			ps.getProduct(paramMap);
			ArrayList< HashMap<String , Object> > list
				=(ArrayList< HashMap<String , Object> >) paramMap.get("ref_cursor");
			HashMap<String , Object> pvo = list.get(0);
			String kindList[] = { "Heels", "Boots", "Sandals", "Snickers", "Slipers",  "Sale" };
			mav.addObject("kindList", kindList );
			mav.addObject("dto", pvo);
			mav.setViewName("admin/product/productUpdate");
		}
		return mav;
	}
	
	@RequestMapping(value="/productUpdate", method=RequestMethod.POST)
	public String productUpdate( HttpServletRequest request , Model model) {
		HashMap<String , Object>paramMap = new HashMap<String, Object>();
		
		paramMap.put("pseq", request.getParameter("pseq") );
		paramMap.put("kind", request.getParameter("kind") );
		paramMap.put("name", request.getParameter("name") );
		paramMap.put("price1", request.getParameter("price1") );
		paramMap.put("price2", request.getParameter("price2") );
		paramMap.put("price3", request.getParameter("price3") );
		paramMap.put("content", request.getParameter("content") );
		
		if( request.getParameter("useyn") == null)
			paramMap.put("useryn", "N" );
		else
			paramMap.put("useryn", "Y" );
		
		if( request.getParameter("bestyn") == null)
			paramMap.put("bestyn", "N" );
		else
			paramMap.put("bestyn", "Y" );
		
		paramMap.put("image", request.getParameter("oldfilename") );
		model.addAttribute("dto" , paramMap);
		model.addAttribute("newImage", request.getParameter("image"));
		
		
		if( request.getParameter("name") == null || request.getParameter("name").equals("") )
			return "admin/product/productUpdate";
		else if( request.getParameter("price1") == null || request.getParameter("price1").equals("") )
			return "admin/product/productUpdate";
		else if( request.getParameter("price2") == null || request.getParameter("price2").equals("") )
			return "admin/product/productUpdate";
		else if( request.getParameter("price3") == null || request.getParameter("price3").equals("") )
			return "admin/product/productUpdate";
		else if( request.getParameter("content") == null || request.getParameter("content").equals("") )
			return "admin/product/productUpdate";
		else { 
			if( request.getParameter("image")!=null || !request.getParameter("image").equals("") )
				paramMap.put("image", request.getParameter("image") );
			
			as.updateProduct( paramMap );
			return "redirect:/adminProductDetail?pseq=" + request.getParameter("pseq"); 
		}
		
		
	}
	
	
	
	
	
	@RequestMapping("/memberList")
	public ModelAndView memberList( HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		if( session.getAttribute("loginAdmin")==null) 
			mav.setViewName("admin/adminLoginForm");
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("request", request);
			as.getMemberList( paramMap );
			mav.addObject("memberList",  
					(ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor")  );
			mav.addObject("paging", (Paging)paramMap.get("paging") );
			mav.addObject("key", (String)paramMap.get("key") );
			mav.setViewName("admin/member/memberList");
		}
		
		return mav;
	}
	
	
	@RequestMapping("/memberReinsert")
	public String memberReinsert( @RequestParam("id") String id, 
			@RequestParam("useyn") String useyn	) {
		
		if( useyn.equals("Y") ) useyn="N";
		else useyn="Y";
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("useyn", useyn);
		
		as.memberReinsert( paramMap );
		return "redirect:/memberList";
	}
	
	
	
	@RequestMapping("/adminOrderList")
	public ModelAndView adminOrderList( HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		if( session.getAttribute("loginAdmin")==null) 
			mav.setViewName("admin/adminLoginForm");
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("request", request);
			as.getOrderList( paramMap );
			mav.addObject("orderList",  
					(ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor")  );
			mav.addObject("paging", (Paging)paramMap.get("paging") );
			mav.addObject("key", (String)paramMap.get("key") );
			mav.setViewName("admin/order/orderList");
		}
		
		return mav;
	}
	
	
	@RequestMapping("/orderUpdateResult")
	public String orderUpdateResult( @RequestParam("result") int [] results  ) {
		
		// 전달된 results 속에 담긴 odseq 들을 하나씩 검색해서 result 값을 다음값으로 변경
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("results", results);
		as.updateOrderResult( paramMap );
		
		return "redirect:/adminOrderList";
	}
	
	
	
	
	
	@RequestMapping("/adminQnaList")
	public ModelAndView adminQnaList( HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		if( session.getAttribute("loginAdmin")==null) 
			mav.setViewName("admin/adminLoginForm");
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("request", request);
			as.getQnaList( paramMap );
			mav.addObject("qnaList",  
					(ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor")  );
			mav.addObject("paging", (Paging)paramMap.get("paging") );
			mav.addObject("key", (String)paramMap.get("key") );
			mav.setViewName("admin/qna/qnaList");
		}
		
		return mav;
	}
	
	
	@Autowired
	QnaService qs;
	
	@RequestMapping("/adminQnaView")
	public ModelAndView adminQnaView( @RequestParam("qseq") int qseq   	) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("qseq", qseq);
		paramMap.put("ref_cursor", null);
		qs.getQna(paramMap);
		ArrayList<HashMap<String, Object>> list 
			= (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
		HashMap<String, Object> qvo = list.get(0);
		mav.addObject("qnaVO", qvo );
		mav.setViewName("admin/qna/qnaView");
		return mav;
	}
	
	
	
	
	@RequestMapping(value="/adminQnaRepSave", method=RequestMethod.POST)
	public String adminQnaRepSave( @RequestParam("qseq") int qseq,
								@RequestParam("reply") String reply ) {
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("qseq", qseq);
		paramMap.put("reply", reply);
		
		as.updateQna( paramMap );
		return "redirect:/adminQnaView?qseq=" + qseq;
	}
	
	
	
	
	
	
	
	@RequestMapping("/adminBannerList")
	public ModelAndView bannerList( ) {
		ModelAndView mav = new ModelAndView();
	
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ref_cursor", null);
		as.getBannerList(paramMap);
		ArrayList<HashMap<String, Object>> list 
			= (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
		
		mav.addObject("bannerList",  list);
		mav.setViewName("admin/banner/bannerList");
		
		return mav;
	}
	
	
	
	
	@RequestMapping("/newBannerWrite")
	public String newBannerWrite() {
		return "admin/banner/writeBanner";
	}
	
	
	@RequestMapping(value="/bannerWrite" )
	public String bannerWrite( HttpServletRequest request	) {		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("subject", request.getParameter("subject") );
		paramMap.put("order_seq", request.getParameter("order_seq") );
		paramMap.put("image", request.getParameter("image") );
		
		if( request.getParameter("order_seq").equals("6") ) paramMap.put("useyn", "N" );
		else paramMap.put("useyn", "Y" );
		
		as.insertBanner( paramMap );
		return "redirect:/adminBannerList";
	}
	
	
	
	@RequestMapping("/chane_order")
	public String chane_order(
			HttpServletRequest request,
			@RequestParam("bseq") int bseq,
			@RequestParam("changeval") int changeval  	) {
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bseq", bseq );	
		String useyn;
		if( changeval > 5) paramMap.put("useyn", "N" );
		else paramMap.put("useyn", "Y" );
		paramMap.put("changeval", changeval );
		
		as.updateSeq( paramMap );
		
		return "redirect:/adminBannerList";
	}

	
}





















