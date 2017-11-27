package top.felixu.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.felixu.annotation.MyAnnotation;

/**
 * @author: felix.
 * @createTime: 2017/11/27.
 */
@Aspect
@Component
public class MyAspect {

    @Before("@annotation(myAnnotation)")
    public void beforeTest(JoinPoint point, MyAnnotation myAnnotation) throws Throwable {
        System.out.println("beforeTest:" + myAnnotation.name());
    }

    @After("@annotation(myAnnotation)")
    public void afterTest(JoinPoint point, MyAnnotation myAnnotation) {
        System.out.println("afterTest:" + myAnnotation.name());
    }

    @Before("pointcut()")
    public void before() {
        System.out.println("execution方式执行");
    }

    @Pointcut("execution(* top.felixu.service..*.*(..))")
    public void pointcut() {}
}
