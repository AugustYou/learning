package top.felixu.annotation;

import java.lang.annotation.*;

/**
 * @author: felix.
 * @createTime: 2017/11/27.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyAnnotation {
    String value() default "";
    String name() default "";
}
