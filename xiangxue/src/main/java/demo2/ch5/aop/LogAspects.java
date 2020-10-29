package demo2.ch5.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogAspects {
    @Pointcut("execution(public int demo2.ch5.aop.Caculator.*(..))")
    public void pointCut() {

    }

    // @Before("execution(public int demo2.ch5.aop.Caculator.div(int,int))")
    @Before("pointCut()")
    public void logStart() {
        System.out.println("start");
    }

    @After("pointCut()")
    public void logEnd() {
        System.out.println("end");
    }

    @AfterReturning("pointCut()")
    public void logReturn() {
        System.err.println("success return {}");
    }

    @AfterThrowing("pointCut()")
    public void logException() {
        System.out.println("exception  {}");
    }

    @Around("pointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) {
        Object proceed = null;
        System.out.println("around before");
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        System.out.println("around after");
        return proceed;
    }
}
