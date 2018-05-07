package top.felixu.demo.aspect;

/**
 * @Author felixu
 * @Date 2018/5/7
 */
public class LogAspect {

    public void before() {
        System.out.println("Invoke Method Before~~~");
    }

    public void after() {
        System.out.println("Invoke Method After~~~");
    }
}
