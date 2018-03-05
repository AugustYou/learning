package top.felixu.jdk;

/**
 * @author : felixu
 * @createTime : 2018/3/1.
 */
public class Felixu implements Person{

    public String name = "felixu";

    @Override
    public void findHouse() {
        System.out.println("---felixu---");
        System.out.println("I need beautiful house");
        System.out.println("---felixu---");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
