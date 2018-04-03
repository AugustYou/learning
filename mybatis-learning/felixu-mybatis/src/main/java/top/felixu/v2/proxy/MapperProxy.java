package top.felixu.v2.proxy;

import top.felixu.v2.sqlsession.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

/**
 * @author felixu
 * @datetime 2018/4/2 15:54
 */
public class MapperProxy implements InvocationHandler {

    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Map<String, String> methodSql = sqlSession.getConfiguration().getMapperRegistry().getObject(method.getDeclaringClass().getName());
        if (methodSql != null) {
            return sqlSession.selectOne(methodSql.get(method.getName()), method.getReturnType(), (Integer) args[0]);
        }
        return method.invoke(this, args);
    }
}
