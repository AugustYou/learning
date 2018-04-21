package top.felixu.factory;

import top.felixu.entity.Bmw;
import top.felixu.entity.Car;

/**
 * @author felixu
 * @datetime 2018/4/6 05:30
 */
public class BmwFactory implements CarFactory {
    public Car getCar() {
        return new Bmw();
    }
}
