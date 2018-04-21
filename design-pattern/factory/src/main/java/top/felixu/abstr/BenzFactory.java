package top.felixu.abstr;

import top.felixu.abstr.entity.Benz;
import top.felixu.abstr.entity.BenzSuv;
import top.felixu.entity.Car;

/**
 * @author felixu
 * @datetime 2018/4/8 07:23
 */
public class BenzFactory extends CarFactory {

    @Override
    public Car createSuv() {
        return new BenzSuv();
    }

    @Override
    public Car createNor() {
        return new Benz();
    }
}
