package top.felixu.framework.context;

/**
 * @Author felixu
 * @Date 2018/5/5
 */
public abstract class AbstractApplicationContext {

    protected void onRefresh() {
        // for subclasses : do nothing by default.
    }

    /**
     * refresh bean factory
     */
    protected abstract void refreshBeanFactory();
}
