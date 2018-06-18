package com.myin.team25.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myin.team25.domain.MemberVo;
import com.myin.team25.persistence.MemberService_Mapper;

@Service("memberServiceImpl")
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	SqlSession sqlSession;

	public ArrayList<MemberVo> selectMemberAll() {		
		
		ArrayList<MemberVo> alist = null;
		MemberService_Mapper msm = sqlSession.getMapper(com.myin.team25.persistence.MemberService_Mapper.class);
		alist = msm.selectMemberAll();			

		return alist;
	}

	public int insertMember(int maxMidx, String memberId, String memberName, String memberPassword, long memberJumin,
			String memberEmail, String memberAddr, String memberSex, String memberIp, String memberWriteday, int bidx) {

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("maxMidx", maxMidx);
		map.put("memberId", memberId);
		map.put("memberName", memberName);
		map.put("memberPassword", memberPassword);
		map.put("memberJumin", memberJumin);
		map.put("memberEmail", memberEmail);
		map.put("memberAddr", memberAddr);
		map.put("memberSex", memberSex);
		map.put("memberIp", memberIp);
		map.put("memberWriteday", memberWriteday);
		map.put("bidx", bidx);
		
		MemberService_Mapper msm = sqlSession.getMapper(com.myin.team25.persistence.MemberService_Mapper.class);
		int result = msm.insertMember(map);	

		return result;
	}

	public int maxMember() {

		MemberService_Mapper msm = sqlSession.getMapper(com.myin.team25.persistence.MemberService_Mapper.class);
		int cnt = msm.maxMember();
		int maxMidx = cnt + 1;		

		return maxMidx;
	}

	public MemberVo loginCheck(String memberId, String memberPassword) {
			
		MemberService_Mapper msm = sqlSession.getMapper(com.myin.team25.persistence.MemberService_Mapper.class);
		MemberVo mv = msm.loginCheck(memberId,memberPassword);			
	
		return mv;
	}
	
	public MemberVo selectMemberOne(String memberId) {
			
		MemberService_Mapper msm = sqlSession.getMapper(com.myin.team25.persistence.MemberService_Mapper.class);
		MemberVo mv = msm.selectMemberOne(memberId);		
		
		return mv;
	}
	
	public int updateMember(int memberMidx, String memberPassword,String memberEmail, String memberAddr, String memberIp, String modifyday, int bidx) {
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("memberMidx", memberMidx);
		map.put("memberPassword", memberPassword);
		map.put("memberEmail", memberEmail);
		map.put("memberAddr", memberAddr);
		map.put("memberIp", memberIp);
		map.put("modifyday", modifyday);
		map.put("bidx", bidx);		
		
		MemberService_Mapper msm = sqlSession.getMapper(com.myin.team25.persistence.MemberService_Mapper.class);
		int result = msm.updateMember(map);	
		
		return result;
	}
	
	
	public int keeplogin(int memberMidx, String sessionkey, String sessionLimit) {
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("memberMidx", memberMidx);
		map.put("sessionkey", sessionkey);
		map.put("sessionLimit", sessionLimit);		
		
		MemberService_Mapper msm = sqlSession.getMapper(com.myin.team25.persistence.MemberService_Mapper.class);
		int result = msm.keepLogin(map);	
		
		return result;
	}
	
	public MemberVo checkAutoLogin(String sessionKey){					
		
		MemberService_Mapper msm = sqlSession.getMapper(com.myin.team25.persistence.MemberService_Mapper.class);
		MemberVo mvo = msm.checkAutoLogin(sessionKey);
		
		return 	mvo;	
	}

}
