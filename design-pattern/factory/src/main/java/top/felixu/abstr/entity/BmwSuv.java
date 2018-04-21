package top.felixu.abstr.entity;

import top.felixu.entity.Car;

/**
 * @author felixu
 * @datetime 2018/4/4 14:45
 */
public class BmwSuv extends IBmw {

    @Override
    public String getType() {
        return "SUV";
    }

    public String getName() {
        return "Is BMW";
    }
}