package top.felixu.factorymethod;

/**
 * @author: felixu
 * @createTime: 2018/1/19.
 */
public class CreateCarFactory extends Factory {
    public <T extends Product> T createCar(Class<T> c) {
        Product product = null;
        try {
            product = (Product) Class.forName(c.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) product;
    }
}
