package top.felixu;

/**
 * @author : felixu
 * @createTime : 2018/3/13.
 */
public class Sex implements Cloneable{
    private String sex;

    public Sex(String sex) {
        this.sex = sex;
    }

    @Override
    public Object clone() {
        Sex sex = null;
        try {
            sex = (Sex) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return sex;
    }
}
