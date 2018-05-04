package top.felixu.framework.beans;

import top.felixu.framework.core.FactoryBean;

/**
 * @Author felixu
 * @Date 2018/4/24
 */
public class BeanWrapper extends FactoryBean {

    /**
     * 对原生对象对包装
     */
    private Object wrapperInstance;

    /**
     * 反射产生的原始对象
     */
    private Object originalInstance;

    /**
     * 观察者模式，一个监听
     */
    private BeanPostProcessor beanPostProcessor;

    public BeanWrapper(Object object) {
        this.wrapperInstance = object;
        this.originalInstance = object;
    }

    public BeanPostProcessor getBeanPostProcessor() {
        return beanPostProcessor;
    }

    public void setBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessor = beanPostProcessor;
    }

    public Object getWrappedInstance() {
        return this.wrapperInstance;
    }

    public Class<?> getWrappedClass() {
        return getWrappedClass().getClass();
    }
}
