package top.felixu.annotation;

import java.lang.annotation.*;

/**
 * autowired instance
 *
 * @Author felixu
 * @Date 2018/4/23
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
