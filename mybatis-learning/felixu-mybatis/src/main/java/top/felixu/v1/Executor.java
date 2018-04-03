package top.felixu.v1;

/**
 * @author felixu
 * @datetime 2018/4/1 22:40
 */
public interface Executor {
    /**
     * 执行查询
     * @param statement
     * @param parameter
     * @param <T>
     * @return
     */
    public <T> T query(String statement, String parameter);
}
