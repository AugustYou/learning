package top.felixu.beans;

/**
 * 用作事件监听
 * @Author felixu
 * @Date 2018/4/25
 */
public class BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }
    public Object postProcessAfterInitialization(Object bean, String beanName){
        return bean;
    }
}
