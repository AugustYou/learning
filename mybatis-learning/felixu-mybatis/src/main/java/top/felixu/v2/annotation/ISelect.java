package top.felixu.v2.annotation;

import java.lang.annotation.*;

/**
 * @author felixu
 * @datetime 2018/4/2 23:10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ISelect {
    String value();
}
