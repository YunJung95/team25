package com.myin.team25;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.myin.team25.domain.BoardVo;
import com.myin.team25.service.BoardService;

@RunWith(SpringJUnit4ClassRunner.class) //junit4를 런에즈 할것임
@ContextConfiguration(locations = {"classpath:config/root-context.xml"})
public class BoardServiceImplTest {

	@Autowired //받고
	private BoardService bs;	
	
	@Before //초기화 시키고
	public void setUp() throws Exception {
		
	}
	
	@Test //이것을 테스트 할것임
	public void testSelectBoardOne() {
		System.out.println("bdf!!!!!!!!!  "+bs);
		BoardVo bv = bs.SelectBoardOne(5); //이 메소드 실행
		//있는 번호 오류 안나고 없는 번호는 오류나야됨
		
		//assertEquals("제목", bv.getSubject()); //예상되는 서브젝트 이름을 junit에게 비슷한 값을 비교해봐라~ 하는것임?
		assertEquals(5, bv.getMemberMidx());
	
		
	}
}
