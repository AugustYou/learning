package top.felixu.factory;

import top.felixu.entity.Benz;
import top.felixu.entity.Car;

/**
 * @author felixu
 * @datetime 2018/4/6 05:27
 */
public class BenzFactory implements CarFactory {
    public Car getCar() {
        return new Benz();
    }
}
