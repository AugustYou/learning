package top.felixu.v1;

/**
 * @author felixu
 * @datetime 2018/4/1 21:09
 */
public class SqlSession {

    private Configuration configuration;
    private Executor executor;

    public SqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T> T getMapper(Class<T> clazz) {
        return configuration.getMapper(clazz, this);
    }

    public <T> T selectOne(String statement, String parameter) {
        return executor.query(statement, parameter);
    }
}
