package top.felixu.beans;

/**
 * 用来保存配置文件中的Bean信息
 * 相当于在内存中存储了一份
 *
 * @Author felixu
 * @Date 2018/4/24
 */
public class BeanDefinition {
    private boolean lazyInit = false;
    private String factoryBeanName;
    private String beanClassName;

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }
}
