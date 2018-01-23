package top.felixu.datasource;

import java.lang.annotation.*;

/**
 * @author : felixu
 * @createTime : 2017/5/22.
 * 在方法上使用注解指定使用哪个数据源
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String name();
}
