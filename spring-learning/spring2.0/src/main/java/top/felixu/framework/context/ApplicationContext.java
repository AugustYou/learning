package top.felixu.framework.context;

import top.felixu.framework.annotation.Autowired;
import top.felixu.framework.annotation.Controller;
import top.felixu.framework.annotation.Service;
import top.felixu.framework.aop.AopConfig;
import top.felixu.framework.beans.BeanDefinition;
import top.felixu.framework.beans.BeanPostProcessor;
import top.felixu.framework.beans.BeanWrapper;
import top.felixu.framework.context.support.BeanDefinitionReader;
import top.felixu.framework.core.BeanFactory;
import top.felixu.framework.utils.Try;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author felixu
 * @Date 2018/4/24
 */
public class ApplicationContext extends DefaultListableBeanFactory implements BeanFactory {

    /**
     * 存储配置文件的路径
     */
    private String[] configLocations;

    /**
     * 持有一个定位解析的Reader
     */
    private BeanDefinitionReader reader;

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
                Object bean = getBean(key);
                System.out.println(bean);
                System.out.println(bean.getClass());
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
                .map(Try.of(bean -> {
                    BeanPostProcessor beanPostProcessor = new BeanPostProcessor();
                    beanPostProcessor.postProcessBeforeInitialization(instance, name);
                    BeanWrapper beanWrapper = new BeanWrapper(bean);
                    beanWrapper.setAopConfig(instantAopConfig(beanDefinition));
                    beanWrapper.setBeanPostProcessor(beanPostProcessor);
                    this.beanWrapperMap.put(name, beanWrapper);
                    populateBean(name, instance);
                    return beanWrapper;
                }))
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

    private AopConfig instantAopConfig(BeanDefinition beanDefinition) throws Exception {
        AopConfig config = new AopConfig();

        String expression = reader.getConfig().getProperty("pointCut");
        String[] before = reader.getConfig().getProperty("aspectBefore").split("\\s");
        String[] after = reader.getConfig().getProperty("aspectAfter").split("\\s");

        String className = beanDefinition.getBeanClassName();
        Class<?> clazz = Class.forName(className);

        Pattern pattern = Pattern.compile(expression);

        Class<?> aspectClass = Class.forName(before[0]);

        for (Method method : clazz.getMethods()) {
            Matcher matcher = pattern.matcher(method.toString());
            if (matcher.matches()) {
                // 将符合切面规则的类，加入到AOP的配置中
                config.put(method, aspectClass.newInstance(), new Method[]{aspectClass.getMethod(before[1]), aspectClass.getMethod(after[1])});
            }
        }
        return config;
    }

    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public Properties getConfig() {
        return this.reader.getConfig();
    }
}