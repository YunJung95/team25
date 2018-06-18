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

public class LoginInterceptor extends HandlerInterceptorAdapter{ 	// ��� ����(HandlerInterceptorAdapter)
	
	//private static final String LOGIN = "login";
	//private static final Logger logger = LoggerFactory.getLogger(Loginlnterceptor.class);
	

	
	@Override		//�ڿ��� ����è  //�ڿ��� üũ
					//��Ʈ�ѷ��� ���� Ư�� �޼ҵ尡 ����ǰ� �� �Ŀ�  �����
	public void postHandle(HttpServletRequest request,   HttpServletResponse response, 
						   Object handler, ModelAndView modelAndView)	
   throws Exception {
		HttpSession session = request.getSession();
		//ModelMap modelMap = modelAndView.getModelMap();
		//Object memberVo = modelMap.get("memberVo");
		
		System.out.println("post ����");
		
		Object sMemberId = modelAndView.getModel().get("sMemberId");
		Object sMemberMidx = modelAndView.getModel().get("sMemberMidx");
		Object sMemberName = modelAndView.getModel().get("sMemberName");
		
		//modelAndView.getModelMap().get �� ��ü ������
		
		if(sMemberMidx != null){ //��Ʈ�ѷ����� ���� �𵨰� ������ ���ǿ� ���
			//session.setAttribute("memberVo", memberVo); //���ǰ��� ������ ����
			request.getSession().setAttribute("sMemberMidx", sMemberMidx);
			request.getSession().setAttribute("sMemberId", sMemberId);
			request.getSession().setAttribute("sMemberName", sMemberName);
			
		//	 System.out.println(request.getParameter("useCookie"));
			
			
			//System.out.println("LoginInterceptor  response"+response);
			
			//��Ű���� �߰�
			 //C c = new C() 
			
			if (request.getParameter("useCookie") != null ) {
				 
				 DateFormat df = new SimpleDateFormat("yyMMdd");   
				 String Cookiedate = df.format(new Date());	 
				 int sessionLimit = Integer.parseInt(Cookiedate);
				 System.out.println("sessionLimit:"+sessionLimit);
				 
				 
				 Cookie loginCookie = new Cookie("loginCookie",request.getSession().getId());
				 loginCookie.setPath("/");
				 loginCookie.setMaxAge(60*60*24*7);		 //7�ϵ��� �����Ѵ�		 
				 response.addCookie(loginCookie);		
			 }
			}
		
		}
	
	
	@Override      //�� �ܿ��� ����è  �տ��� üũ
				//��Ʈ�ѷ��� ���� Ư�� �޼ҵ尡 ����Ǳ����� ���� �����
	public boolean preHandle(HttpServletRequest request,  HttpServletResponse response, Object handler ) 
			throws Exception {
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("sMemberMidx") != null){ //���� ���̺��� ������� �ʱ�ȭ����
			
			System.out.println("LoginInterceptor  pre�Ծ�!");
			//Ŭ���̾�Ʈ�� ������ ��û�ؼ� �����ؼ� ������ �䱸�ϸ� �װͰ� �����Ű�� 
			//������ ������ ���� �ش� Ŭ���̾�Ʈ�� �������մ� /��Ű�� ����?
			session.removeAttribute("sMemberMidx");
			session.removeAttribute("sMemberid");
			session.removeAttribute("sMemberName");
		}
		
		return true;
	}
	
}
