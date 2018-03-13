package top.felixu.staticed;

/**
 * @author : felixu
 * @createTime : 2018/3/12.
 * 这种静态内部类的单例方式，也是属于饿汉式的一种形式
 * 特点在于外部类不被调用的话，内部类是不会被加载的，不存在像饿汉那样的内存浪费
 * 内部类会在方法之前完成初始化，所以也是避免了线程问题，同时也没有用到锁，从而避免了性能问题
 * 是一种推荐的方式
 */
public class StaticSingleton {

    private StaticSingleton() {}

    private static class StaticSingletonHolder {
        private static final StaticSingleton STATIC_SINGLETON = new StaticSingleton();
    }

    public static StaticSingleton getInstance() {
        return StaticSingletonHolder.STATIC_SINGLETON;
    }
}