package top.felixu.framework.context;

import top.felixu.framework.beans.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author felixu
 * @Date 2018/5/5
 */
public class DefaultListableBeanFactory extends AbstractApplicationContext {

    /**
     * BeanDefinitionMap也就是Spring中的IOC容器
     */
    protected final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    @Override
    protected void refreshBeanFactory() {

    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
    }
}