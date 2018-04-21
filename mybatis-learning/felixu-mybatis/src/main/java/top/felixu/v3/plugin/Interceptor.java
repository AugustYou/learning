package top.felixu.v3.plugin;

import java.util.Properties;

/**
 * @author felixu
 * @datetime 2018/4/12 16:33
 */
public interface Interceptor {

    Object intercept(Invocation invocation) throws Throwable;

    Object plugin(Object target);

    void setProperties(Properties properties);
}
