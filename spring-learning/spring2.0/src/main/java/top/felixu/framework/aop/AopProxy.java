package top.felixu.framework.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 默认使用JDK的动态代理
 * @Author felixu
 * @Date 2018/5/6
 */
public class AopProxy implements InvocationHandler {

    private Object target;

    private AopConfig config;

    /**
     * 传入原生对象
     * @param instance
     * @return
     */
    public Object getProxy(Object instance) {
        this.target = instance;
        Class<?> clazz = instance.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    public void setConfig(AopConfig config) {
        this.config = config;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method m = this.target.getClass().getMethod(method.getName(), method.getParameterTypes());
        if (config.contains(m)) {
            AopConfig.Aspect aspect = config.get(m);
            aspect.getPoints()[0].invoke(aspect.getAspect(), args);
        }
        Object obj = method.invoke(this.target, args);
        if (config.contains(m)) {
            AopConfig.Aspect aspect = config.get(m);
            aspect.getPoints()[1].invoke(aspect.getAspect(), args);
        }
        return obj;
    }
}
