package top.felixu.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author : felixu
 * @createTime : 2018/3/2.
 */
public class CgIntermediary implements MethodInterceptor{

    public Object getInstance(Class clazz) {
        Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("---cg intermediary---");
        proxy.invokeSuper(obj, args);
        System.out.println("---cg intermediary---");
        return null;
    }
}