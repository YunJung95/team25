package com.myin.team25.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SampleAdvice {

	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);
	
	@Before("execution(* com.myin.team25.service.BoardServiceImpl*.*(..))")
	public void startLog ()	{
		logger.info("--------_____o___o______--------");
		logger.info("--------_____o___o______--------");
	}
}
