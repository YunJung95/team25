package com.myin.team25.interceptor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.myin.team25.domain.MemberVo;
import com.myin.team25.service.MemberService;

public class AuthInterceptor extends HandlerInterceptorAdapter {
		
	
	@Autowired
	MemberService ms;
	
	
	 @Override //로그인 안했으면 얘가 잡아서 로그인 페이지로 보냄 ((앞으로 이동될 페이지)갈려고 하는 페이지 주소를 가지고 감)
		public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response,Object handler) throws Exception{
			System.out.println("왔냐! preHandle");
				HttpSession session = request.getSession();
		 
		 
		 
		 //로그인 하지 않은 경우
		 if(session.getAttribute("sMemberMidx") == null){ //sMemberMidx가 없으면 
			 System.out.println("sMemberMidx 빔");
			
			 //이동할 주소를 담음
			 saveDest(request); //가고싶은 페이지req.getRequestURL(),req.getQueryString() 이두개
			 System.out.println("로그인 후 가야할 페이지" +request );
			 //WebUtils.getCookie() 꺼냄, checkAutoLogin(쿠키에 담긴 세션 key값,쿠키에 담긴 세션 날짜,회원 정보?(midx))
			 
			// response.sendRedirect(request.getContextPath()+"/MemberLoginController");  //로그인창
			//본인 pc에 저장된 쿠키정보를 꺼낸다.
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			 System.out.println(loginCookie);
			 //저장된 쿠키에 자동 로그인정보가 있으면
			if (loginCookie  != null){ 
		
			//쿠키에 저장된 키정보와 같은 키가 DB에 있는지 체크해서 있으면 그 회원정보를 담는다 	
				 MemberVo mv =  ms.checkAutoLogin(loginCookie.getValue());
				
				if (mv != null) {
					 
					 //자동로그인 기록이 존재하면 세션에 담고
					 request.getSession().setAttribute("sMemberId", mv.getMemberId());
					 request.getSession().setAttribute("sMemberMidx", mv.getMemberMidx());
					 request.getSession().setAttribute("sMemberName", mv.getMemberName());	
					
					 Cookie loginCookie2 = new Cookie("loginCookie",loginCookie.getValue());
					 loginCookie2.setPath("/");
					 loginCookie2.setMaxAge(60*60*24*7);
					 response.addCookie(loginCookie2);	
					 
					 // DB 테이블에도 날짜 갱신  //loginCookie는 값을 가지고 있을 수없듬
					 Calendar cal = Calendar.getInstance();
					 cal.setTime(new Date());
					 cal.add(Calendar.DATE, 7);
					 DateFormat df1 = new SimpleDateFormat("yy-MM-dd");   
					 String sessionLimit = df1.format(cal.getTime());
					 
					 ms.keeplogin(mv.getMemberMidx(), loginCookie.getValue(), sessionLimit);				
					 System.out.println("왔냐!!");
				 }else {
					 response.sendRedirect(request.getContextPath()+"/MemberLoginController");
					 System.out.println("왔냐아!!!!");
					 return false;
				 }
				 response.sendRedirect(request.getContextPath()+"/MemberLoginController");
				 System.out.println("왔어어???????");
				 return false;
			 }
			 
		 }
		 System.out.println("왔어????");
		 return true; //로그인 페이지에서 로그인이 되면 밑에 정보를 가지고 		
	 	}
	
	 private void saveDest(HttpServletRequest req){ //이동하려고 하는 페이지를 던져주는
		 System.out.println("왔다!!! saveDest");
		 String uri = req.getRequestURI(); 		 
		 String query = req.getQueryString(); //?뒤에 파라미터
		 
		 
		 if (query ==null || query.equals("null")){
			 query = "";
		 }else {
			 query = "?" + query;
		 }
		 
		 if (req.getMethod().equals("GET")){
			 req.getSession().setAttribute("dest", uri+query);  //url이랑 query랑 담음
			 
			 System.out.println("이동해야할 페이지uri:"+uri+query);
		 }		 
	 }	
}
