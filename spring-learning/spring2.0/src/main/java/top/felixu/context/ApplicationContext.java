package top.felixu.context;

import top.felixu.annotation.Autowired;
import top.felixu.annotation.Controller;
import top.felixu.annotation.Service;
import top.felixu.beans.BeanDefinition;
import top.felixu.beans.BeanPostProcessor;
import top.felixu.beans.BeanWrapper;
import top.felixu.core.BeanFactory;
import top.felixu.support.BeanDefinitionReader;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author felixu
 * @Date 2018/4/24
 */
public class ApplicationContext implements BeanFactory {

    /**
     * 存储配置文件的路径
     */
    private String[] configLocations;

    /**
     * 持有一个定位解析的Reader
     */
    private BeanDefinitionReader reader;

    /**
     * BeanDefinitionMap也就是Spring中的IOC容器
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /**
     * 用了存储实例化bean，保证单例
     */
    private final Map<String, Object> beanCacheMap = new ConcurrentHashMap<>(256);

    /**
     * 存储包装后的对象实例
     */
    private final Map<String, BeanWrapper> beanWrapperMap = new ConcurrentHashMap<>(256);

    public ApplicationContext(String... locations) {
        this.configLocations = locations;
        refresh();
    }

    public void refresh() {
        // 定位
        this.reader = new BeanDefinitionReader(this.configLocations);
        // 加载
        // 注册
        doRegistryBeanDefinition(this.reader.loadBeanDefinitions());
        // 注入
        doDependencyInject();
        System.out.println("==============");
    }

    /**
     * 这里模拟自动化注入操作
     */
    private void doDependencyInject() {
        this.beanDefinitionMap.forEach((key, value) -> {
            if (!value.isLazyInit()) {
                getBean(key);
            }
        });

    }

    public void populateBean(String beanName, Object instance) {
        Class<?> clazz = instance.getClass();
        if (clazz.isAnnotationPresent(Controller.class)
                || clazz.isAnnotationPresent(Service.class)) {
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Autowired.class))
                    .forEach(field -> {
                        Autowired autowired = field.getAnnotation(Autowired.class);
                        String autowiredBeanName = autowired.value().trim();
                        if ("".equals(autowiredBeanName)) {
                            autowiredBeanName = field.getType().getName();
                        }
                        field.setAccessible(true);
                        try {
                            BeanWrapper beanWrapper = this.beanWrapperMap.get(autowiredBeanName);
                            Object dependency = null;
                            if (beanWrapper != null) {
                                dependency = beanWrapper.getWrappedInstance();
                            } else {
                                dependency = ((BeanWrapper) getBean(autowiredBeanName)).getWrappedInstance();
                            }
                            field.set(instance, dependency);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
        }

    }

    /**
     * 真正往IOC容器中注册BeanDefinition的方法
     * 注册时分为三种情况
     * 1. 默认情况下以类名首字母小写注入
     * 2. 自定义名称注入
     * 3. 接口注入实现类
     * @param beanDefinitions
     */
    private void doRegistryBeanDefinition(List<String> beanDefinitions) {
        Optional.ofNullable(beanDefinitions)
                .ifPresent(beans -> beans.stream().forEach(beanName -> {
                    try {
                        Class<?> clazz = Class.forName(beanName);
                        // 不是接口
                        if (!clazz.isInterface()) {
                            BeanDefinition beanDefinition = reader.registryBeanDefinition(beanName);
                            Optional.ofNullable(beanDefinition)
                                    .ifPresent(definition -> this.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition));
                            Class<?>[] interfaces = clazz.getInterfaces();
                            Arrays.stream(interfaces)
                                    .forEach(i -> this.beanDefinitionMap.put(i.getName(), beanDefinition));
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }));

    }

    /**
     * 先获取BeanDefinition，然后通过反射创建实例并返回
     * Spring中不会返回原始对象，而是返回一个由BeanWrapper包装过的对象
     * 即装饰器模式
     * 保证原有OOP关系
     * 可以对其进行增强，为AOP做准备
     * @param name
     * @return
     */
    @Override
    public Object getBean(String name) {
        if (this.beanWrapperMap.containsKey(name)) {
            return this.beanWrapperMap.get(name).getWrappedInstance();
        }
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(name);
        Object instance = instantBean(beanDefinition);
        return Optional.ofNullable(instance)
                .map(bean -> {
                    BeanPostProcessor beanPostProcessor = new BeanPostProcessor();
                    beanPostProcessor.postProcessBeforeInitialization(instance, name);
                    BeanWrapper beanWrapper = new BeanWrapper(bean);
                    beanWrapper.setBeanPostProcessor(beanPostProcessor);
                    this.beanWrapperMap.put(name, beanWrapper);
                    populateBean(name, instance);
                    return beanWrapper;
                })
                .orElse(null);
    }

    private Object instantBean(BeanDefinition beanDefinition) {
        String className = beanDefinition.getBeanClassName();
        try {
            Class<?> clazz = Class.forName(className);
            synchronized (this.beanCacheMap) {
                if (this.beanCacheMap.containsKey(className)) {
                    return this.beanCacheMap.get(className);
                }
                Object instance = clazz.newInstance();
                this.beanCacheMap.put(className, instance);
                return instance;
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}