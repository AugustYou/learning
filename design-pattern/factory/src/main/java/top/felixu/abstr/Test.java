package top.felixu.abstr;

import top.felixu.abstr.entity.IBenz;
import top.felixu.entity.Car;

/**
 * @author felixu
 * @datetime 2018/4/8 07:45
 */
public class Test {
    public static void main(String[] args) {
        CarFactory factory = new BenzFactory();
        Car car = factory.createSuv();
        System.out.println(car.getName());
        System.out.println(((IBenz) car).getType());
    }
}
