package top.felixu.factory;

import top.felixu.entity.Car;

/**
 * @author felixu
 * @datetime 2018/4/4 17:28
 */
public class Test {
    public static void main(String[] args) {
//        CarFactory carFactory = new CarFactoryImpl();
//        Car car = carFactory.createCar(IBenz.class);
//        System.out.println(car.getName());
        CarFactory factory = new BmwFactory();
        Car car = factory.getCar();
        System.out.println(car.getName());
    }
}
