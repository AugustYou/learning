package top.felixu.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author : felixu
 * @createTime : 2018/3/1.
 */
public class Intermediary implements InvocationHandler {

    private Person target;

    public Object getInstance(Person target) {
        this.target = target;
        Class<? extends Person> clazz = target.getClass();
        System.out.println("proxy obj is: " + clazz);
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("---intermediary---");
        method.invoke(target, args);
        System.out.println("oh~find one");
        System.out.println("---intermediary---");
        return null;
    }
}
