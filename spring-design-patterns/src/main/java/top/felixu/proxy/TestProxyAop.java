package top.felixu.proxy;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;

/**
 * @author: felixu
 * @createTime: 2018/1/11.
 */
public class TestProxyAop {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        ProxyFactory factory = new ProxyFactory(new House());
        factory.addInterface(Construction.class);
        factory.addAdvice(new BeforeConstructAdvice());
        factory.setExposeProxy(true);

        Construction construction = (Construction) factory.getProxy();
        construction.construct();
        if (construction.isPermitted()) {
            System.out.println("Construction is illegal. "
                    + "Supervisor didn't give a permission to build "
                    + "the house");
        }
    }
}

interface Construction {
    public void construct();
    public void givePermissin();
    public boolean isPermitted();
}

class House implements Construction {

    private boolean permitted = false;

    @Override
    public void construct() {
        System.out.println("I'm constructing a house");
    }

    @Override
    public void givePermissin() {
        System.out.println("Permission is given to construct a simple house");
        this.permitted = true;
    }

    @Override
    public boolean isPermitted() {
        return this.permitted;
    }
}

class BeforeConstructAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] arguments, Object target) throws Throwable {
        if (method.getName().equals("construct")) {
            ((Construction) target).givePermissin();
        }
    }
}


