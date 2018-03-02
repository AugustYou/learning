package top.felixu.my;

import java.lang.reflect.Method;

/**
 * @author : felixu
 * @createTime : 2018/3/1.
 */
public interface MyInvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
