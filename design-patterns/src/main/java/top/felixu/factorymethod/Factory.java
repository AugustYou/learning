package top.felixu.factorymethod;

/**
 * @author: felixu
 * @createTime: 2018/1/19.
 */
public abstract class Factory {
    public abstract <T extends  Product> T createCar(Class<T> c);
}
