package top.felixu.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author felixu
 * @datetime 2018/4/7 14:53
 */
public class BeanFactory {

    private BeanFactory() {}

    private static Map<String, Object> register = new ConcurrentHashMap<String, Object>();

    /**
     * 虽然是用了ConcurrentHashMap，但是再多线程并发情况下getBean依然是有线程安全问题
     * 而Spring中是使用这种注册方法的单例，不知道Spring中是如何保证线程安全的
     * @param className
     * @return
     */
    public static Object getBean(String className) {
        Object obj = null;
        if (register.containsKey(className)) {
            obj = register.get(className);
        } else {
            try {
                obj = Class.forName(className).newInstance();
                register.put(className, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
}
