package com.myin.team25.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.myin.team25.domain.MemberVo;

public interface MemberService {	
	
	public ArrayList<MemberVo> selectMemberAll();
	
	public int insertMember(int maxMidx, String memberId, String memberName, String memberPassword, long memberJumin,
			String memberEmail, String memberAddr, String memberSex, String memberIp, String memberWriteday, int bidx);
	
	public int maxMember();
	
	public MemberVo loginCheck(String memberId, String memberPassword);
	
	public MemberVo selectMemberOne(String memberId);
	
	public int updateMember(int memberMidx, String memberPassword,String memberEmail, String memberAddr, String memberIp, String Modifyday, int bidx);

	public int keeplogin(int memberMidx, String sessionKey, String sessionLimit);
	
	public MemberVo checkAutoLogin(String sessionKey);
}
