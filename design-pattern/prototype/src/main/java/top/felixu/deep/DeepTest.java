package top.felixu.deep;

import top.felixu.Sex;

/**
 * @author : felixu
 * @createTime : 2018/3/13.
 * 深拷贝需要目标对象中的引用类型变量也要去实现Cloneable接口，在目标对象实现clone方法时，也需要将引用变量拷贝
 * 深拷贝并不是那么容易实现的，它需要引用变量都去实现Cloneable接口，重写clone方法，在实际操作中这是很难实现的
 */
public class DeepTest {
    public static void main(String[] args) {
        Person1 p = new Person1("felixu", 26, new Sex("man"));
        Person1 p1 = (Person1) p.clone();
        System.out.println("原始对象:" + p + " 姓名:" + p.getName() + " 年龄:" + p.getAge() + " 性别:" + p.getSex());
        System.out.println("原始对象:" + p1 + " 姓名:" + p.getName() + " 年龄:" + p1.getAge() + " 性别:" + p1.getSex());
        System.out.println("是否为同一个对象:" + (p == p1));
        System.out.println("是否为浅拷贝:" + (p.getSex() == p1.getSex()));
    }
}
