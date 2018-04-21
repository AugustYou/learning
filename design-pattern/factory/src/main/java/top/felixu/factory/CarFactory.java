package top.felixu.factory;

import top.felixu.entity.Car;

/**
 * @author felixu
 * @datetime 2018/4/4 16:46
 */
public interface CarFactory {
//    public <T extends Car> T createCar(Class<T> clazz);

    public Car getCar();
}
