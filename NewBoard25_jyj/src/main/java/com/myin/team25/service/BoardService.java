package com.myin.team25.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.myin.team25.domain.BoardVo;
import com.myin.team25.domain.SearchCriteria;

public interface BoardService {
	
	public ArrayList<BoardVo> SelectBoardAll(SearchCriteria scri);
	
	public BoardVo SelectBoardOne(int bbidx);
	
	public int insertBoard(BoardVo bv);
	
	public int updateBoard(String subject, String content, String writer, String password,
			String ip, int bbidx, int midx);
	
	public int deleteBoard(String password,String ip, int bbidx, int midx);

	public int replyBoard(int bbidx, int oidx, int updown, int leftright, int midx, String subject,
			String content, String writer, String password, String memberIp);

	public int totalRecordCount(SearchCriteria scri);
	
	public ArrayList<HashMap<String, Object>> boardMemberInfo();
}
