package com.myin.team25.controller;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myin.team25.domain.CommentCriteria;
import com.myin.team25.domain.CommentVo;
import com.myin.team25.service.CommentService;

import oracle.net.aso.c;

@RestController
public class CommentController {
	
	@Autowired//주입
	private CommentService cs; 
						//comments에 전체 bbidx
	@RequestMapping(value="/comments/all/{bbidx}")
	public ArrayList<CommentVo> commentList(@PathVariable("bbidx") int bbidx){
		//System.out.println("bbidx:"+ bbidx);
		
		ArrayList<CommentVo> alist =  cs.SelectCommentAll(bbidx);
		
		return alist;	//페이지 이름을 리턴하는게 아니라 객체(alist)를 던져줌 넘긴값은 알아서 json파일로 바뀜?
						//localhost/comments/all/200
	}	
	
	
	//댓글 부분 가져오기
	@RequestMapping(value="/comments/{block}/{bbidx}")
	public HashMap<String, Object> moreCommentList(@PathVariable("bbidx") int bbidx, //꺼낼때 int 형으로 바꿈
												@PathVariable("block") int block ){ 
		//System.out.println("bbidx:"+ bbidx);
		CommentCriteria cc = new CommentCriteria();
		
		int defaultBlock = cc.getBlock();
		int perBlockNum = cc.getPerBlockNum();
		//int nextBlock = cc.getNextBlock();//다음 댓글을 볼수있게 
		
		if ( block == 0) { //하드코딩을 넣어놨지만 혹시나 싶어서 만들어놓음
			block = defaultBlock;
		}
		ArrayList<CommentVo> alist = cs.getCommentMore(bbidx, block, perBlockNum);
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("alist", alist);
		hm.put("nextBlock", block+1); //3이나옴
		//System.out.println("hm :"+ hm);
		//System.out.println("hm nextBlock : "+ hm.get("nextBlock"));
		
		
		return hm; 		
	}	
	
	
	
	
							//REST 레스트 방식/comments/{cidx}/{bbidx}
	///*produces="text/plain;charset=UTF-8"*/
	@RequestMapping(value="/comments/del/{cidx}/{bbidx}", method=RequestMethod.GET) //@PathVariable 경로 변수
	public ResponseEntity<String> CommentDelete(@PathVariable("cidx") int cidx,
											  @PathVariable("bbidx") int bbidx,
											  HttpServletRequest request) 
	throws Exception{
		System.out.println("삭제 들어옴");
		HttpSession session = request.getSession();		
		int sMemberMidx = (int) session.getAttribute("sMemberMidx");
		
		InetAddress local = InetAddress.getLocalHost();		
		String ip  = local.getHostAddress();
		
		CommentVo cv = new CommentVo();		
		cv.setCidx(cidx);
		cv.setMemberMidx(sMemberMidx);
		cv.setIp(ip);
		
		//int result = 0;
		String msg = null;
		ResponseEntity<String> entity = null;
		
		try {
		//result = cs.deleteComment(cv);
		cs.deleteComment(cv);
		msg = "삭제됨";
		
		entity = new ResponseEntity<String>(msg,HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}	//에러가 날수도 있으니까 
		
		//ArrayList<CommentVo> alist =  cs.SelectCommentAll(bbidx); 리스트 기능
		//commentList(bbidx); 리스트를 뿌릴수없음 entity를 리턴하기 때문에 
		System.out.println("등록시 entity "+entity);
		System.out.println("등록시 entity.body "+entity.getBody());
		return entity; //alist를 뿌려주었는데 이번ㅇ는 '삭제됨'이라는 메세지를 뿌리고 리스트를 불러오기 위해서  
	}
																	
	@RequestMapping(value="/comments", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	public ResponseEntity<String> CommentWrite(@RequestBody CommentVo cv,
											HttpServletRequest request) 
	throws Exception{	
		
		System.out.println("ggg:"+cv.getBbidx());		
		
		InetAddress local = InetAddress.getLocalHost();		
		String ip  = local.getHostAddress();
		
		HttpSession session = request.getSession();		
		int sMemberMidx = (int) session.getAttribute("sMemberMidx");
		
		cv.setIp(ip);
		cv.setMemberMidx(sMemberMidx);
		
		ResponseEntity<String> entity = null;
		String msg = null;
		try {
			cs.insertComment(cv);
			msg = "등록됨";
			entity = new ResponseEntity<String>(msg,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
				
	//	ArrayList<CommentVo> alist =  cs.SelectCommentAll(cv.getBbidx());
		System.out.println("등록시 entity :" +entity.getBody());
		
		return entity;
	}
	
	
}
