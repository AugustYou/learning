package top.felixu.framework.core;

/**
 * @Author felixu
 * @Date 2018/4/24
 */
public interface BeanFactory {

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * @param name
     * @return
     */
    Object getBean(String name);
}
