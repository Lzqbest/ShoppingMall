package com.aspect;

import com.common.BaseException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;


/**
 * @author liaozhiqiang1
 * @date 2021/8/2 16:08
 * @Description
 */

@Aspect
@Component
public class AutoLoginAspect {

    @Pointcut("execution(* com.controller..*(..))")
    public void pointCut(){}

    @Before(value = "pointCut()")
    public void before(JoinPoint joinPoint){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        System.out.println(request);
        Object[] args = joinPoint.getArgs();
        if (Objects.nonNull(args)){
            Arrays.stream(args).forEach(System.out::println);
        }
        throw new BaseException("请先登录");
    }
}
