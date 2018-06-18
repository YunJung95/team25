package com.myin.team25.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
@Aspect
public class BoardTimeCheckAdvice {

	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);
	
	@Around("execution(* com.myin.team25.service.BoardService*.*(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable { //���� ����Ʈ�� �޾Ƽ� ����ϴ� �������̽�
		
		logger.info("Before AOP==========================================================");
		long startTime = System.currentTimeMillis(); //���� �ð�
		logger.info(Arrays.toString(pjp.getArgs()));
		
		Object result = pjp.proceed();
		
		long endTime = System.currentTimeMillis(); //������ �ð�
		logger.info("After AOP==========================================================");
		logger.info("�޼ҵ� "+pjp.getSignature().getName() + " : " +(endTime - startTime));
		logger.info("end?==========================================================");
		
		return result;
	}
}
