//package top.felixu.factory;
//
//import top.felixu.entity.Car;
//
///**
// * @author felixu
// * @datetime 2018/4/4 17:20
// */
//public class CarFactoryImpl implements CarFactory {
//    public <T extends Car> T createCar(Class<T> clazz) {
//        T instance = null;
//        try {
//            instance = clazz.newInstance();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
//        return instance;
//    }
//}
