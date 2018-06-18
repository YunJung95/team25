package com.myin.team25.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.myin.team25.domain.MemberVo;
import com.myin.team25.service.MemberService;

@Controller
public class MemberController {	
	
	@Autowired(required = false) //(required = false) 못찾아도 좋아 빈값이 어도 좋아 그러니까 에러내지마! 라는 뜻임
	MemberService ms;		
	
	@RequestMapping(value="/MemberListController")
	public String memberList(Model model) {
		
		ArrayList<MemberVo> alist =  ms.selectMemberAll();		
		model.addAttribute("alist", alist);		
		
		return "member/memberList";
	}
	
	@RequestMapping(value="/MemberJoinController")
	public String memberJoin() {		
		
		return "member/memberJoin";
	}
	
	@RequestMapping(value="/MemberJoinActionController")
	public String memberJoinAction(@ModelAttribute("mv") MemberVo mv,@RequestParam("memberEmail1") String memberEmail1,@RequestParam("memberEmail2") String memberEmail2) throws UnknownHostException {
				
		String memberEmail = memberEmail1 + "@" + memberEmail2;
		
		int maxMidx = ms.maxMember();
		
		InetAddress local = InetAddress.getLocalHost();		
		String memberIp = local.getHostAddress();
		
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String memberWriteday = sdf.format(dt);
		memberWriteday = memberWriteday.substring(2);
		
	//	System.out.println(maxMidx);
		
		int res = ms.insertMember(maxMidx, mv.getMemberId(), mv.getMemberName(), mv.getMemberPassword(), mv.getMemberJumin(), memberEmail, mv.getMemberAddr(), mv.getMemberSex(), memberIp, memberWriteday, mv.getBidx());
		
		String page = null;
		if (res ==1){
			page = "redirect:/MemberLoginController";
		}else{
			page = "redirect:/MemberJoinController";
		}
		
		return page;
	}
	
	
	//logout
	@RequestMapping(value="/MemberLogoutController", method = RequestMethod.GET)
	public String memberLogout(HttpServletRequest request, HttpServletResponse response, HttpSession session) 
	throws Exception{		
		
		//DB 삭제
		int sMemberMidx = (int)session.getAttribute("sMemberMidx");
		ms.keeplogin(sMemberMidx, "", "");	
		
		//세션 삭제
		session.removeAttribute("sMemberMidx");
		session.removeAttribute("sMemberId");
		session.removeAttribute("sMemberName");
		session.invalidate();
		
		//쿠키 삭제		
		Cookie loginCookie = WebUtils.getCookie(request, "loginCookie"); 
		if (loginCookie != null) {
			 loginCookie.setPath("/");		
			 loginCookie.setMaxAge(0);				 
			 response.addCookie(loginCookie);			
		}
		
		return "member/memberLogout";
	}
	
	@RequestMapping(value="/MemberLoginController")
	public String memberLogin() {		
		
		return "member/memberLogin";
	}
	
	@RequestMapping(value="/MemberLoginActionController")
	public String memberLoginAction(HttpServletRequest request,@RequestParam("memberId") 
	String memberId,@RequestParam("memberPassword") String memberPassword, 
	@RequestParam(name = "useCookie", required = false) String useCookie, Model model, HttpSession session) 
	throws IOException{ //required 필수라는 뜻 값이 넘어오는데 @RequestParam이 객체 생성해서 useCookie여기에 담았는데 
						// 클래스에 필수조건은 값이 있어야 하는데(필수) useCookie는 값이 안담아질때도 잇어서 
							//필수로 꼭 값을 담을 필요는 없다고 빈값이여도 받으라고 선언해주는것 
		//HttpSession session = request.getSession();
		System.out.println("loginActincontroller 왔음");
		//MemberVo mv = ms.loginCheck(memberId, memberPassword);
		
		int res = 0;
		String page =null;
		MemberVo mv = null;
		String dest = null;
		
		mv = ms.loginCheck(memberId, memberPassword);
		System.out.println("LoginAction mv :::" +mv);
		
		if (mv == null) {				
			
			}else {
				
				System.out.println("값 들어옴");
				model.addAttribute("sMemberId", mv.getMemberId());
				model.addAttribute("sMemberMidx",mv.getMemberMidx() );
				model.addAttribute("sMemberName", mv.getMemberName());
				
				
				
				
				//자동로그인 체크 하면 실행
				if(useCookie != null) {
					//impl keeplogin 불러서 db입력
					System.out.println("자동로그인 체크를 하였습니다.");
					Calendar cal = Calendar.getInstance();
				    cal.setTime(new Date());
				    cal.add(Calendar.DATE, 7);
				    DateFormat df = new SimpleDateFormat("yy-MM-dd");   
				    String next = df.format(cal.getTime());
				   
				    //	System.out.println(next);
					
					ms.keeplogin(mv.getMemberMidx(), session.getId(), next);
			} else {
				System.out.println("자동로그인 체크를 하지 않았습니다.");
			}
				
			res = 1;	
			dest = (String)session.getAttribute("dest");
		}		
		
		if (res ==1) {
			if(dest != null){
				page = "redirect:"+dest+"";
				System.out.println("dest page :::" +page);
			}else {
				page = "redirect:/MemberListController";
				}
		}else{
			page = "redirect:/MemberLoginController";
		}
		
		System.out.println("page"+page);
		return page;
	}
	
	@RequestMapping(value="/MemberModifyController")
	public String memberModify(HttpServletRequest request,Model model)  {
		
		HttpSession session = request.getSession();		
		String sMemberId = (String) session.getAttribute("sMemberId");	
			
		MemberVo mv = ms.selectMemberOne(sMemberId);	
		model.addAttribute("mv", mv);	
		
	
		return "member/memberModify";
	}
	
	
	@RequestMapping(value="/MemberModifyActionController")
	public String memberModifyAction(HttpServletRequest request,
					@RequestParam("memberEmail1") String memberEmail1,
					@RequestParam("memberEmail2") String memberEmail2,
					@RequestParam("memberAddr") String memberAddr,
					@RequestParam("memberPassword") String memberPassword,
					@RequestParam("bidx") int bidx_int,
					Model model) throws UnknownHostException, UnsupportedEncodingException  {		
				
		String memberEmail = memberEmail1 + "@" + memberEmail2;		
			
		HttpSession session = request.getSession();		
		int sMemberMidx = (int) session.getAttribute("sMemberMidx");
			
		InetAddress local = InetAddress.getLocalHost();		
		String memberIp  = local.getHostAddress();			
			
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String Modifyday = sdf.format(dt);
		Modifyday = Modifyday.substring(2);		
		
		ms.updateMember(sMemberMidx, memberPassword, memberEmail, memberAddr, memberIp, Modifyday, bidx_int);			
		
		String page = null; 
		
	//	if (res ==1) {
	//		page = "redirect:/MemberContentController";
	//	}else{
			page = "redirect:/MemberModifyController";
	//	}
		
		return page;
	}
	
	
}








