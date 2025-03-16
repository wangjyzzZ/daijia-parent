package com.atguigu.daijia.common.mock.impl;

import com.atguigu.daijia.common.mock.MockDataEnum;
import com.atguigu.daijia.common.mock.MockDataStrategy;
import com.atguigu.daijia.common.mock.MockDataStrategyFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component
public class LoginMockStrategy implements MockDataStrategy {

    @Override
    public void afterPropertiesSet() throws Exception {
        MockDataStrategyFactory.registerStrategy(MockDataEnum.LOGIN, this);
    }

    @Override
    public Object run(ProceedingJoinPoint proceedingJoinPoint) {
        return 1L;
    }
}
