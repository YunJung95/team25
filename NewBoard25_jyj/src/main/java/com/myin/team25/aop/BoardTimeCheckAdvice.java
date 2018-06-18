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
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable { //조인 포인트를 받아서 사용하는 인터페이스
		
		logger.info("Before AOP==========================================================");
		long startTime = System.currentTimeMillis(); //시작 시간
		logger.info(Arrays.toString(pjp.getArgs()));
		
		Object result = pjp.proceed();
		
		long endTime = System.currentTimeMillis(); //끝나는 시간
		logger.info("After AOP==========================================================");
		logger.info("메소드 "+pjp.getSignature().getName() + " : " +(endTime - startTime));
		logger.info("end?==========================================================");
		
		return result;
	}
}
