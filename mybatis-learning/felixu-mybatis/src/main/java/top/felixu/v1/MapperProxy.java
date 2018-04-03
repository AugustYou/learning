package top.felixu.v1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author felixu
 * @datetime 2018/4/1 23:09
 */
public class MapperProxy implements InvocationHandler {

    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        /**
         * invoke方法将会是使用SqlSession调用selectOne方法的。
         * 所以必然MapperProxy要持有SqlSession
         * 1. 获取命名空间对应的sql语句
         * 2. 调用selectOne方法
         */
        if (Configuration.PersonMapperXml.NAME_SPACE.equals(method.getDeclaringClass().getName())) {
            String sql = Configuration.PersonMapperXml.SQL_MAPPING.get(method.getName());
            return sqlSession.selectOne(sql, String.valueOf(args[0]));
        }
        return method.invoke(this, args);
    }
}