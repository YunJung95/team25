package com.myin.team25.interceptor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{ 	// 상속 받음(HandlerInterceptorAdapter)
	
	//private static final String LOGIN = "login";
	//private static final Logger logger = LoggerFactory.getLogger(Loginlnterceptor.class);
	

	
	@Override		//뒤에서 가로챔  //뒤에서 체크
					//컨트롤러에 가서 특정 메소드가 실행되고 난 후에  실행됨
	public void postHandle(HttpServletRequest request,   HttpServletResponse response, 
						   Object handler, ModelAndView modelAndView)	
   throws Exception {
		HttpSession session = request.getSession();
		//ModelMap modelMap = modelAndView.getModelMap();
		//Object memberVo = modelMap.get("memberVo");
		
		System.out.println("post 들어옴");
		
		Object sMemberId = modelAndView.getModel().get("sMemberId");
		Object sMemberMidx = modelAndView.getModel().get("sMemberMidx");
		Object sMemberName = modelAndView.getModel().get("sMemberName");
		
		//modelAndView.getModelMap().get 은 객체 꺼낼때
		
		if(sMemberMidx != null){ //컨트롤러에서 담은 모델값 꺼내서 세션에 담기
			//session.setAttribute("memberVo", memberVo); //세션값을 꺼내서 담음
			request.getSession().setAttribute("sMemberMidx", sMemberMidx);
			request.getSession().setAttribute("sMemberId", sMemberId);
			request.getSession().setAttribute("sMemberName", sMemberName);
			
		//	 System.out.println(request.getParameter("useCookie"));
			
			
			//System.out.println("LoginInterceptor  response"+response);
			
			//쿠키값을 추가
			 //C c = new C() 
			
			if (request.getParameter("useCookie") != null ) {
				 
				 DateFormat df = new SimpleDateFormat("yyMMdd");   
				 String Cookiedate = df.format(new Date());	 
				 int sessionLimit = Integer.parseInt(Cookiedate);
				 System.out.println("sessionLimit:"+sessionLimit);
				 
				 
				 Cookie loginCookie = new Cookie("loginCookie",request.getSession().getId());
				 loginCookie.setPath("/");
				 loginCookie.setMaxAge(60*60*24*7);		 //7일동안 보관한다		 
				 response.addCookie(loginCookie);		
			 }
			}
		
		}
	
	
	@Override      //앞 단에서 가로챔  앞에서 체크
				//컨트롤러에 가서 특정 메소드가 실행되기전에 먼저 실행됨
	public boolean preHandle(HttpServletRequest request,  HttpServletResponse response, Object handler ) 
			throws Exception {
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("sMemberMidx") != null){ //같은 테이블이 있을까봐 초기화해줌
			
			System.out.println("LoginInterceptor  pre왔어!");
			//클라이언트가 서버에 요청해서 접속해서 뭔가를 요구하면 그것과 연결시키는 
			//세션은 서버에 담은 앤대 클라이언트네 나줘져잇는 /쿠키에 저장?
			session.removeAttribute("sMemberMidx");
			session.removeAttribute("sMemberid");
			session.removeAttribute("sMemberName");
		}
		
		return true;
	}
	
}
