package com.felix.learning.dynamicdatasource.datasource;

import java.lang.annotation.*;

/**
 * @author : felix.
 * @createTime : 2017/4/10.
 * @desc : 注解指定使用哪个数据源
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String name();
}
