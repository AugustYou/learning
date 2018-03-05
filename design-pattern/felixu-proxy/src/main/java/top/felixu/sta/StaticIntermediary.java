package top.felixu.sta;

import top.felixu.jdk.Person;

/**
 * @author : felixu
 * @createTime : 2018/3/2.
 */
public class StaticIntermediary implements Person {

    private Person target;

    public StaticIntermediary(Person target) {
        this.target = target;
    }

    @Override
    public void findHouse() {
        System.out.println("---static intermediary---");
        target.findHouse();
        System.out.println("oh~~~find one");
        System.out.println("---static intermediary---");
    }
}
