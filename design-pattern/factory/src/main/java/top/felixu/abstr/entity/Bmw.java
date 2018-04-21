package top.felixu.abstr.entity;

/**
 * @author felixu
 * @datetime 2018/4/8 07:33
 */
public class Bmw extends IBmw {
    public String getName() {
        return "Is BMW";
    }

    @Override
    public String getType() {
        return "普通型";
    }
}
