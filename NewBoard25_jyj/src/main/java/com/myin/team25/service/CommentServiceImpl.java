package com.myin.team25.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession; //s
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myin.team25.domain.BoardVo;
import com.myin.team25.domain.CommentVo;
import com.myin.team25.persistence.BoardService_Mapper;
import com.myin.team25.persistence.CommentService_Mapper;

@Service("commentServiceImpl") //빈에 요청한것과 똑같음 
public class CommentServiceImpl implements CommentService {

	@Autowired	
	SqlSession sqlSession;
	
	@Override
	public ArrayList<CommentVo> SelectCommentAll(int bbidx) {
	
		//System.out.println("comment bbidx:"+bbidx);
		ArrayList<CommentVo> alist = null;
		CommentService_Mapper csm = sqlSession.getMapper(com.myin.team25.persistence.CommentService_Mapper.class);
		//System.out.println("csm:"+csm);
		alist = csm.SelectCommentAll(bbidx);
		//System.out.println("a:"+alist);
		return alist;
	}
	
	
	
	
	
	@Override
	public ArrayList<CommentVo> getCommentMore(int bbidx, int block, int perBlockNum) {
	
		
		ArrayList<CommentVo> alist = null;
		CommentService_Mapper csm = sqlSession.getMapper(com.myin.team25.persistence.CommentService_Mapper.class);
		alist = csm.getCommentMore(bbidx,block,perBlockNum);
		//System.out.println("a:"+alist);
		return alist;
	}
	
	
	
	
	

	@Override
	public int insertComment(CommentVo cv) {		
				
		CommentService_Mapper csm = sqlSession.getMapper(com.myin.team25.persistence.CommentService_Mapper.class);
		int result = csm.insertComment(cv);	

		return result;
	}

	@Override
	public int deleteComment(CommentVo cv) {
		
		CommentService_Mapper csm = sqlSession.getMapper(com.myin.team25.persistence.CommentService_Mapper.class);
		int result = csm.deleteComment(cv);		
		
		return result;
	}

}
