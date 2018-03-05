package top.felixu.my;

import top.felixu.jdk.Person;

import java.lang.reflect.Method;

/**
 * @author : felixu
 * @createTime : 2018/3/1.
 */
public class MyIntermediary implements MyInvocationHandler {

    private Person target;

    public Object getInstance(Person target) {
        this.target = target;
        Class<? extends Person> clazz = target.getClass();
        System.out.println("obj is: " + clazz);
        return MyProxy.newProxyInstance(new MyClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("---my intermediary---");
        method.invoke(target, args);
        System.out.println("oh~find one");
        System.out.println("---my intermediary---");
        return null;
    }
}
