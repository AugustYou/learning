package top.felixu.compare;

import top.felixu.Person;
import top.felixu.Sex;

/**
 * @author : felixu
 * @createTime : 2018/3/13.
 * 区分复制对象和复制对象引用
 * 从结果来看，p和p1指向相同的地址，p和p1只是将p的引用复制到p1上了，他们实际指向的对象还是同一个
 * 而p和p2指向的地址是不同的，这说明p和p2他们指向的对象不是同一个，p2通过clone重新产生了一个对象
 */
public class CopyTest {
    public static void main(String[] args) {
        Person p = new Person("felixu", 26, new Sex("男"));
        // 复制引用，我们都知道，p其实是对象的引用，指向对象真实地址
        Person p1 = p;
        System.out.println("复制引用:" + p1 + " 姓名:" + p1.getName() + " 年龄:" + p1.getAge() + " 性别:" + p1.getSex());
        // 复制对象，重新申请空间，将对象复制到新的空间上
        Person p2 = (Person) p.clone();
        System.out.println("复制对象:" + p2 + " 姓名:" + p2.getName() + " 年龄:" + p2.getAge() + " 性别:" + p2.getSex());
        System.out.println("原始对象:" + p + " 姓名:" + p.getName() + " 年龄:" + p.getAge() + " 性别:" + p.getSex());
    }
}
