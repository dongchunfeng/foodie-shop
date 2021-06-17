package com.itzixue.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/12/5
 */
@Slf4j
@Aspect
@Component
public class ServiceLogAspect {

    /**
     * aop通知
     * 1.前置通知  在方法调用之前执行
     * 2.后置通知  在方法正常调用之后执行
     * 3.环绕通知  在方法调用之前和调用之后执行
     * 4.最后通知  在方法调用之后执行
     */


    @Around("execution(* com.itzixue.service.impl..*.*(..))")
    public Object returnLogAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("===========开始执行 {}.{}=============", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());

        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long end = System.currentTimeMillis();
        long takeTime = end - start;

        if (takeTime > 3000) {
            log.error("=======执行结束 耗时 {} 毫秒=======", takeTime);
        } else if (takeTime > 2000) {
            log.warn("=======执行结束 耗时 {} 毫秒=======", takeTime);
        } else {
            log.info("=======执行结束 耗时 {} 毫秒=======", takeTime);
        }
        return proceed;
    }


}
