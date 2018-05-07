package top.felixu.framework.context;

/**
 * @Author felixu
 * @Date 2018/5/5
 */
public interface ApplicationContextAware {

    /**
     * set application
     * @param applicationContext
     */
    void setApplicationContext(ApplicationContext applicationContext);
}