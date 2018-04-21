package top.felixu.v3.interceptor;

import top.felixu.v3.executor.Executor;
import top.felixu.v3.plugin.*;
import java.util.Properties;

/**
 * @author felixu
 * @datetime 2018/3/30 15:54
 */
@Intercepts({@Signature(type = Executor.class,
        method = "query",
        args = {String.class, Class.class, Integer.class})})
public class MyInterceptorOne implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.err.println("form my interceptor one");
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
