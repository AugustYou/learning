package top.felixu.abstr.entity;

/**
 * @author felixu
 * @datetime 2018/4/8 07:33
 */
public class Benz extends IBenz {
    public String getName() {
        return "Is Benz";
    }

    @Override
    public String getType() {
        return "普通型";
    }
}
