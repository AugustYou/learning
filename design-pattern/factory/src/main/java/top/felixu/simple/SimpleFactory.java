package top.felixu.simple;

import top.felixu.entity.Bmw;
import top.felixu.entity.Benz;
import top.felixu.entity.Car;

/**
 * @author felixu
 * @datetime 2018/4/4 14:51
 */
public class SimpleFactory {
    public static Car getCar(String name) {
        if ("IBmw".equals(name)) {
            return new Bmw();
        } else if ("IBenz".equals(name)) {
            return new Benz();
        } else {
            throw new RuntimeException("car's name is not exist");
        }
    }
}
