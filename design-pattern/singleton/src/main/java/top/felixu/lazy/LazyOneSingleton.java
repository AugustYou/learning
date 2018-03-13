package top.felixu.lazy;

/**
 * @author : felixu
 * @createTime : 2018/3/12.
 * 这种方式在第一次调用时候才去创建对象
 * 线程不安全
 */
public class LazyOneSingleton {

    private static LazyOneSingleton singleton = null;

    private LazyOneSingleton(){}

    public static LazyOneSingleton getInstance() {
        if (singleton == null) {
            singleton = new LazyOneSingleton();
        }
        return singleton;
    }
}
