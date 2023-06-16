package com.ezen.g14.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g14.dto.KakaoProfile;
import com.ezen.g14.dto.KakaoProfile.KakaoAccount;
import com.ezen.g14.dto.KakaoProfile.KakaoAccount.Profile;
import com.ezen.g14.dto.MemberVO;
import com.ezen.g14.dto.OAuthToken;
import com.ezen.g14.service.MemberService;
import com.google.gson.Gson;

@Controller
public class MemberController {

   @Autowired
   MemberService ms;
   
   @RequestMapping("/")
   public String root() {
      return "member/loginForm";
   }
   
   
   //@ModelAttribute("dto") 실패했을때 가져가기위한것
   //BindingResult result 에러확인?
   @RequestMapping(value="/login", method=RequestMethod.POST)
   public String login(@ModelAttribute("dto") @Valid MemberVO MemberVO,
                        BindingResult result, HttpServletRequest request, Model model) {
      
      String url ="member/loginForm";
      if(result.getFieldError("userid")!=null) {
         model.addAttribute("message", result.getFieldError("userid").getDefaultMessage());
      }else if(result.getFieldError("pwd")!=null) {
         model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
      }else {
         // 아이디 비번이 정상입력
         MemberVO mvo =ms.getMember(MemberVO.getUserid());
         if(mvo==null) {
            model.addAttribute("message", "아이디가 없습니다");
            return "member/loginForm";
         }
            
         else if(mvo.getPwd()==null) {
            model.addAttribute("message", "비밀번호 오류. 관리자에게 문의하세요");
            return "member/loginForm";
         }
            
         else if(!mvo.getPwd().equals(MemberVO.getPwd())) {
            model.addAttribute("message", "비밀번호가 맞지않습니다");
            return "member/loginForm";
         }
            
         else if(mvo.getPwd().equals(MemberVO.getPwd())) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", mvo );
            url= "redirect:/main";
         }
            
      }
      
      return url;   
   }
   
   @RequestMapping(value="/logout", method=RequestMethod.GET)
   public String logout(HttpServletRequest request) {
      HttpSession session = request.getSession();
      session.invalidate();
      return "redirect:/";
   }
   
   @RequestMapping("/kakaoLogin")
   public String login(HttpServletRequest request) throws UnsupportedEncodingException, IOException{
         
      String code = request.getParameter("code");
      
      String endpoint ="https://kauth.kakao.com/oauth/token";
      URL url = new URL(endpoint);
      
      String bodyData="grant_type=authorization_code&";
      bodyData += "client_id=262e918b5675b24289ca7b6493e959ff&";
      bodyData +="redirect_uri=http://localhost:8070/kakaoLogin&";
      bodyData +="code="+code;
   
      //Stream 연결
      HttpURLConnection conn =(HttpURLConnection)url.openConnection();
      
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
      conn.setDoOutput(true);
      
      BufferedWriter bw = new BufferedWriter(
            new OutputStreamWriter(conn.getOutputStream(),"UTF-8")
            );
      bw.write(bodyData);
      bw.flush();
      BufferedReader br = new BufferedReader(
            new InputStreamReader(conn.getInputStream(),"UTF-8")
            );
      String input="";
      StringBuilder sb =new StringBuilder();
      while((input = br.readLine())!=null) {
         sb.append(input);
         System.out.println(input);
      }
      
      // 여기서부터Gson으로 파싱
      Gson gson=new Gson();
            
   
      OAuthToken oAuthToken=gson.fromJson(sb.toString(), OAuthToken.class);
      String endpoint2 = "https://kapi.kakao.com/v2/user/me";
      URL url2 = new URL(endpoint2);
      
      HttpURLConnection conn2 =(HttpURLConnection)url2.openConnection();
      
      conn2.setRequestProperty("Authorization", "Bearer "+oAuthToken.getAccess_token());
      conn2.setDoOutput(true);
      
      BufferedReader br2 = new BufferedReader(
            new InputStreamReader(conn2.getInputStream(),"UTF-8")
            );
      String input2="";
      StringBuilder sb2 =new StringBuilder();
      while((input2 = br2.readLine())!=null) {
         sb2.append(input2);
         System.out.println(input2);
      }
      
      
      Gson gson2=new Gson();
      KakaoProfile kakaoProfile=gson2.fromJson(sb2.toString(), KakaoProfile.class);
      
      
      System.out.println(kakaoProfile.getId());
      
      
      KakaoAccount ac =kakaoProfile.getAccount();
      System.out.println(ac.getEmail());
      
      Profile pf =ac.getProfile();
      System.out.println(pf.getNickname());
            
      
      //kakao 로부터 얻은 정보로 member 테이블에서 조회
      MemberVO mvo = ms.getMember(kakaoProfile.getId());
      if (mvo== null) {
         mvo= new MemberVO();
         mvo.setUserid(kakaoProfile.getId());
         mvo.setEmail(ac.getEmail());
         mvo.setName(pf.getNickname());
         mvo.setProvider("kakao");
         mvo.setPwd("kakao");
         mvo.setPhone("");
         
         ms.insertMember(mvo);
         
      }
      
      HttpSession session =request.getSession();
      session.setAttribute("loginUser", mvo);
      
      
      
      
      return "redirect:/main";
   }
   @RequestMapping("/kakaostart")
   public String kakaostart(){
       String a = "<script type='text/javascript'>"
               + "location.href='https://kauth.kakao.com/oauth/authorize?client_id=262e918b5675b24289ca7b6493e959ff&redirect_uri=http://localhost:8070/kakaoLogin&response_type=code'"
               + "</script>";
       return a;
	   
	   
	   
	   
   }
     
   @RequestMapping("/memberJoinForm")
   public String memberJoinForm(){
	   
	   return "member/memberJoinForm";
   }
   
   @RequestMapping("/idCheck")
   public ModelAndView idCheck(@RequestParam("userid") String userid){
	   ModelAndView mav = new ModelAndView();
	   MemberVO mvo = ms.getMember(userid);
       if( mvo == null )mav.addObject("result", -1);
       else mav.addObject("result", 1);
       mav.addObject("userid", userid);
       mav.setViewName("member/idcheck");
       return mav;
}

	
   @PostMapping("/memberJoin")
   public ModelAndView memberJoin(
		   			@ModelAttribute("dto") @Valid MemberVO membervo,
		   			BindingResult result,
		   			@RequestParam(value= "re_id", required=false) String re_id,
		   			@RequestParam(value= "pwd_check", required=false) String pwd_check
		   		) {
	   ModelAndView mav = new ModelAndView();
	   //밸리데이션으로 전송된 값들을 점검하고, 널이나 빈칸이 있으면 memberJoinForm.jsp로 되돌아가셈
	   //MemberVO로 자동되지 않는 전달인수 = pwd_check, re_id 들은 별도의 변수로 전달받고, 별도의 이상유무를 체크
	   //이상이 있으면 memberJoinForm.jsp로 되돌아가도록
	   //이때 re_id도 mav에 별도 저장하고 되돌아감
	   //모두 이상이 없다고 점검되면 회원가입하고 회원가입완료라는 메세지와 함께 loginForm.jsp로 되돌아감
	   
	   mav.setViewName("member/memberJoinForm"); //되돌아갈 페이지의 기본은 회원가입페이지
	   mav.addObject("re_id",re_id);
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   return mav;
   }
	
}
	

