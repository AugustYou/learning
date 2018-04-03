package top.felixu.v2.executor;

/**
 * @author felixu
 * @datetime 2018/4/2 09:30
 */
public interface Executor {
    /**
     * 瞎特么查
     * @param sql
     * @param returnType
     * @param parameter
     * @param <T>
     * @return
     */
    <T> T query(String sql, Class<T> returnType, Integer parameter);
}
