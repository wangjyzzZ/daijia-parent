package com.atguigu.daijia.common.mock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MockDataAspect {

    @Around("execution(* com.atguigu.daijia..*(..)) && @annotation(mockData)")
    public Object mock(ProceedingJoinPoint proceedingJoinPoint, MockData mockData) {
        MockDataEnum value = mockData.value();
        MockDataStrategy strategy = MockDataStrategyFactory.getStrategy(value);
        return strategy.run(proceedingJoinPoint);
    }
}
