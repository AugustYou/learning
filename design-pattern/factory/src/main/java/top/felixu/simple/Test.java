package top.felixu.simple;

import top.felixu.entity.Benz;
import top.felixu.entity.Car;

/**
 * @author felixu
 * @datetime 2018/4/4 14:57
 */
public class Test {
    public static void main(String[] args) {
        // 没有工厂的时候，所有事情自己亲历其为，即我想要辆车，得自己制造，对应于这里new的操作
//        Car car = new IBenz();
//        System.out.println(car.getName());

        // 有了工厂之后，我想要一辆车，已经不需要再自己去制造了，而是选择去找工厂，告诉工厂我想要什么车
        Car car = SimpleFactory.getCar("IBmw");
        System.out.println(car.getName());
    }
}
