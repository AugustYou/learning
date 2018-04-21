package top.felixu.abstr;

import top.felixu.abstr.entity.IBenz;
import top.felixu.abstr.entity.IBmw;
import top.felixu.entity.Car;

/**
 * @author felixu
 * @datetime 2018/4/8 07:23
 */
public abstract class CarFactory {

    public abstract Car createSuv();

    public abstract Car createNor();
}
