package top.felixu.deep;

import top.felixu.Person;
import top.felixu.Sex;

/**
 * @author : felixu
 * @createTime : 2018/3/13.
 */
public class Person1 implements Cloneable {

    private String name;
    private int age;
    private Sex sex;

    public Person1(String name, int age, Sex sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public Object clone(){
        Person1 person = null;
        try {
            person = (Person1) super.clone();
            sex = (Sex) sex.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return person;
    }
}
