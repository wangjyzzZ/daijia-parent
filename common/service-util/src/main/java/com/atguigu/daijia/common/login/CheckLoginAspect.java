package com.atguigu.daijia.common.login;

import com.atguigu.daijia.common.constant.RedisConstant;
import com.atguigu.daijia.common.execption.GuiguException;
import com.atguigu.daijia.common.result.ResultCodeEnum;
import com.atguigu.daijia.common.util.AuthContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class CheckLoginAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Around("execution(* com.atguigu.daijia.*.controller.*.*(..)) && @annotation(checkLogin)")
    public Object login(ProceedingJoinPoint proceedingJoinPoint, CheckLogin checkLogin)  throws Throwable {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)attributes;
        HttpServletRequest request = sra.getRequest();

        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            throw new GuiguException(ResultCodeEnum.LOGIN_AUTH);
        }

        String customerId = (String)redisTemplate.opsForValue().get(RedisConstant.USER_LOGIN_KEY_PREFIX+token);

        if (StringUtils.hasText(customerId)) {
            AuthContextHolder.setUserId(Long.parseLong(customerId));
        }

        //6 执行业务方法
        return proceedingJoinPoint.proceed();
    }
}
