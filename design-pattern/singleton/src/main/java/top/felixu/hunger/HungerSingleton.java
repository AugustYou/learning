package top.felixu.hunger;

/**
 * @author : felixu
 * @createTime : 2018/3/9.
 * 在类加载时完成初始化，并且实例化
 * 在线程之前完成实例化，所以绝对线程安全
 * 实例化 = 初始化 + 赋值
 */
public class HungerSingleton {

    private static final HungerSingleton HUNGER_SINGLETON = new HungerSingleton();

    private HungerSingleton(){}

    public static HungerSingleton getInstance() {
        return HUNGER_SINGLETON;
    }

    // 还可以用静态代码块，与这种方式一样
//    private static  HungerSingleton instance = null;
//    static {
//        instance = new HungerSingleton();
//    }
//    public static HungerSingleton getInstance() {
//        return instance;
//    }
}