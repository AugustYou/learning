package top.felixu.simple;

import top.felixu.Person;
import top.felixu.Sex;

/**
 * @author : felixu
 * @createTime : 2018/3/13.
 * 浅拷贝
 * clone方法执行的是对象的浅拷贝，即只拷贝了对象，对象中的引用变量拷贝的还是它的引用，从结果中可以看到
 */
public class SimpleTest {
    public static void main(String[] args) {
        Person p = new Person("felixu", 26, new Sex("man"));
        Person p1 = (Person) p.clone();
        System.out.println("原始对象:" + p + " 姓名:" + p.getName() + " 年龄:" + p.getAge() + " 性别:" + p.getSex());
        System.out.println("原始对象:" + p1 + " 姓名:" + p.getName() + " 年龄:" + p1.getAge() + " 性别:" + p1.getSex());
        System.out.println("是否为同一个对象:" + (p == p1));
        System.out.println("是否为浅拷贝:" + (p.getSex() == p1.getSex()));
    }
}
