package top.felixu.framework.utils;

import top.felixu.framework.aop.AopProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * @Author felixu
 * @Date 2018/5/7
 */
public class AopProxyUtils {

    public static Object getSingletonTarget(Object candidate) throws Exception {
        if (isAopProxy(candidate)) {
            return getProxyTargetObject(candidate);
        }
        return candidate;
    }

    private static boolean isAopProxy(Object object) {
        return Proxy.isProxyClass(object.getClass());
    }

    /**
     * 投机取巧的从代理对象中取得原对象
     * @param proxy
     * @return
     * @throws Exception
     */
    private static Object getProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);
        Field target = aopProxy.getClass().getDeclaredField("target");
        target.setAccessible(true);
        return target.get(aopProxy);
    }
}
