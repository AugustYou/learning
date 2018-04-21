package top.felixu.abstr.entity;

import top.felixu.entity.Car;

/**
 * @author felixu
 * @datetime 2018/4/4 14:53
 */
public class BenzSuv extends IBenz {

    @Override
    public String getType() {
        return "SUV";
    }

    public String getName() {
        return "Is Benz";
    }
}
