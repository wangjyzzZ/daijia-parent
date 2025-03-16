package com.atguigu.daijia.common.mock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.InitializingBean;

public interface MockDataStrategy extends InitializingBean {

    Object run(ProceedingJoinPoint proceedingJoinPoint);
}
