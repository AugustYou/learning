package top.felixu.jdk;

import top.felixu.cglib.CgIntermediary;
import top.felixu.my.MyIntermediary;
import top.felixu.sta.StaticIntermediary;

/**
 * @author : felixu
 * @createTime : 2018/3/1.
 */
public class Test {

    public static void main(String[] args) {
//        new Felixu().findLove();
//        Person obj = (Person)new Intermediary().getInstance(new Felixu());
//        System.out.println("proxy class is: " + obj.getClass());
//        obj.findHouse();
//        Person obj = (Person) new MyIntermediary().getInstance(new Felixu());
//        System.out.println("proxy class is: " + obj.getClass());
//        obj.findHouse();
//        StaticIntermediary obj = new StaticIntermediary(new Felixu());
//        System.out.println("proxy class is: " + obj.getClass());
//        obj.findHouse();
//        try {
			Felixu obj = (Felixu)new CgIntermediary().getInstance(Felixu.class);
			obj.findHouse();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

    }
}
