package com.myin.team25.service;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.myin.team25.domain.BoardVo;
import com.myin.team25.domain.Criteria;
import com.myin.team25.domain.SearchCriteria;
import com.myin.team25.persistence.BoardService_Mapper;

@Service("boardServiceImpl")
public class BoardServiceImpl implements BoardService {
	
	@Autowired	
	SqlSession sqlSession;

	@Override
	public BoardVo SelectBoardOne(int bbidx) {
		
		BoardVo bv = null;
		BoardService_Mapper bsm = sqlSession.getMapper(com.myin.team25.persistence.BoardService_Mapper.class);
		bv = bsm.SelectBoardOne(bbidx);
		
		return bv;
	}
	

	@Override
	public ArrayList<BoardVo> SelectBoardAll(SearchCriteria scri) {
		
		ArrayList<BoardVo> alist = null;
		BoardService_Mapper bsm = sqlSession.getMapper(com.myin.team25.persistence.BoardService_Mapper.class);
		alist = bsm.SelectBoardAll(scri);
		
		return alist;
	}	
	


	@Override
	public int insertBoard(BoardVo bv) {

		BoardService_Mapper bsm = sqlSession.getMapper(com.myin.team25.persistence.BoardService_Mapper.class);
		int result = bsm.insertBoard(bv);	

		return result;
	}

	@Override
	public int updateBoard(String subject, String content, String writer, String password, String ip, int bbidx, int midx) {
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("subject", subject);
		map.put("content", content);
		map.put("writer", writer);
		map.put("password", password);
		map.put("ip", ip);
		map.put("bbidx", bbidx);
		map.put("midx", midx);		
		
		BoardService_Mapper bsm = sqlSession.getMapper(com.myin.team25.persistence.BoardService_Mapper.class);
		int result = bsm.updateBoard(map);	
		
		return result;
	}

	@Override
	public int deleteBoard(String password, String ip, int bbidx, int midx) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("bbidx", bbidx);
		map.put("midx", midx);
		map.put("ip", ip);
		map.put("password", password);		
		
		BoardService_Mapper bsm = sqlSession.getMapper(com.myin.team25.persistence.BoardService_Mapper.class);
		int res = bsm.deleteBoard(map);		
		
		return res;
	}

	@Transactional
	@Override
	public int replyBoard(int bbidx, int oidx, int updown, int leftright, int midx, String subject,
			String content, String writer, String password, String memberIp) {
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("bbidx", bbidx);
		map.put("oidx", oidx);
		map.put("updown", updown);
		map.put("leftright", leftright);
		map.put("midx", midx);
		map.put("subject", subject);
		map.put("content", content);
		map.put("writer", writer);
		map.put("password", password);
		map.put("memberIp", memberIp);		
		
		BoardService_Mapper bsm = sqlSession.getMapper(com.myin.team25.persistence.BoardService_Mapper.class);
		bsm.replyUpdateBoard(oidx,updown);
		int res2 = bsm.replyInsertBoard(map);
		
		return res2;
	}	
	
	@Override
	public int totalRecordCount(SearchCriteria scri) {
		
		BoardService_Mapper bsm = sqlSession.getMapper(com.myin.team25.persistence.BoardService_Mapper.class);
		int cnt = bsm.totalRecordCount(scri);
		
		return cnt;		//del_yn='N'°ª¸¸ ÅäÅ»°¹¼ö»Ì´Â°Í
	}

	@Override
	public ArrayList<HashMap<String, Object>> boardMemberInfo() {
		
		ArrayList<HashMap<String, Object>> alist = null;
		BoardService_Mapper bsm = sqlSession.getMapper(com.myin.team25.persistence.BoardService_Mapper.class);
		alist = bsm.boardMemberInfo();
		
		return alist;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
