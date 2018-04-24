package top.felixu.annotation;

import java.lang.annotation.*;

/**
 * @Author felixu
 * @Date 2018/4/23
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}
