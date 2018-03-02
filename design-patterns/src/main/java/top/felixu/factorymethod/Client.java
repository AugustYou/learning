package top.felixu.factorymethod;

/**
 * @author: felixu
 * @createTime: 2018/1/19.
 */
public class Client {
    public static void main(String[] args) {
        Factory factory = new CreateCarFactory();
        System.out.println("buy BWM");
        Product bwm = factory.createCar(BMW.class);
        bwm.method();
        System.out.println("buy Benz");
        Product benz = factory.createCar(Benz.class);
        benz.method();
    }
}
