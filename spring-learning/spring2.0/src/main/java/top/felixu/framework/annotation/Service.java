package top.felixu.framework.annotation;

import java.lang.annotation.*;

/**
 * @Author felixu
 * @Date 2018/4/23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    String value() default "";
}
