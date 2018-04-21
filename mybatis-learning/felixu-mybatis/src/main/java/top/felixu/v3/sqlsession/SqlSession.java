package top.felixu.v3.sqlsession;

import top.felixu.v3.configuration.Configuration;
import top.felixu.v3.executor.Executor;

/**
 * @author felixu
 * @datetime 2018/4/2 09:24
 */
public class SqlSession {

    private Configuration configuration;

    private Executor executor;

    public SqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = configuration.newExecutor(executor);
    }

    public <T> T getMapper(Class<T> clazz) {
        return configuration.getMapper(clazz, this);
    }

    public <T> T selectOne(String sql, Class<T> returnType, Integer parameter) {
        return executor.query(sql, returnType, parameter);
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}