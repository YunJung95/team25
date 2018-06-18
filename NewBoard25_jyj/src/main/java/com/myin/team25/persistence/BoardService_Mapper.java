package com.myin.team25.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.myin.team25.domain.BoardVo;
import com.myin.team25.domain.Criteria;
import com.myin.team25.domain.SearchCriteria;


public interface BoardService_Mapper {
	
	
	public BoardVo SelectBoardOne(int bbidx);
	
	public ArrayList<BoardVo> SelectBoardAll(Criteria cri);
	
	public int insertBoard(BoardVo bv);
	
	public int updateBoard(HashMap<String,Object> map);

	public int deleteBoard(HashMap<String,Object> map);

	public int replyUpdateBoard(int oidx, int updown);

	public int replyInsertBoard(HashMap<String, Object> map);
	
	public int totalRecordCount(SearchCriteria scri);

	public ArrayList<HashMap<String, Object>> boardMemberInfo();

	
	
}
