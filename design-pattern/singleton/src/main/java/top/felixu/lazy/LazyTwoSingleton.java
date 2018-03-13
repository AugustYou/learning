package top.felixu.lazy;

/**
 * @author : felixu
 * @createTime : 2018/3/12.
 * 使用锁解决第一种方式的线程不安全问题，带来的是性能问题
 */
public class LazyTwoSingleton {

    private static LazyTwoSingleton singleton = null;

    private LazyTwoSingleton() {}

    public static synchronized LazyTwoSingleton getInstance() {
        if (singleton == null) {
            singleton = new LazyTwoSingleton();
        }
        return singleton;
    }
}