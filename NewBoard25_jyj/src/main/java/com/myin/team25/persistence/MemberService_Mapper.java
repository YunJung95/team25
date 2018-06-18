package com.myin.team25.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.myin.team25.domain.MemberVo;

public interface MemberService_Mapper {	
	
	public ArrayList<MemberVo> selectMemberAll();
	
	public int insertMember(HashMap<String,Object> map);
	
	public int maxMember();
	
	public MemberVo loginCheck(String memberId, String memberPassword);
	
	public MemberVo selectMemberOne(String memberId);
	
	public int updateMember(HashMap<String,Object> map);
	
	public int keepLogin(HashMap<String,Object> map);

	public MemberVo checkAutoLogin(String sessionKey);
}
