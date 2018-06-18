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

@RunWith(SpringJUnit4ClassRunner.class) //junit4�� ������ �Ұ���
@ContextConfiguration(locations = {"classpath:config/root-context.xml"})
public class BoardServiceImplTest {

	@Autowired //�ް�
	private BoardService bs;	
	
	@Before //�ʱ�ȭ ��Ű��
	public void setUp() throws Exception {
		
	}
	
	@Test //�̰��� �׽�Ʈ �Ұ���
	public void testSelectBoardOne() {
		System.out.println("bdf!!!!!!!!!  "+bs);
		BoardVo bv = bs.SelectBoardOne(5); //�� �޼ҵ� ����
		//�ִ� ��ȣ ���� �ȳ��� ���� ��ȣ�� �������ߵ�
		
		//assertEquals("����", bv.getSubject()); //����Ǵ� ������Ʈ �̸��� junit���� ����� ���� ���غ���~ �ϴ°���?
		assertEquals(5, bv.getMemberMidx());
	
		
	}
}
