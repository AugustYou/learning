package top.felixu.v3;

import top.felixu.v1.Person;
import top.felixu.v3.configuration.Configuration;
import top.felixu.v3.executor.SimpleExecutor;
import top.felixu.v3.interceptor.MyInterceptorOne;
import top.felixu.v3.mappers.PersonMapper;
import top.felixu.v3.plugin.Interceptor;
import top.felixu.v3.sqlsession.SqlSession;

/**
 * @author felixu
 * @datetime 2018/4/3 06:37
 */
public class TestV3 {

    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .mapperScan("top.felixu.v3.mappers")
                .addPlugins(new Interceptor[]{new MyInterceptorOne()})
                .build();
        SqlSession sqlSession = new SqlSession(configuration, new SimpleExecutor());
        PersonMapper mapper = sqlSession.getMapper(PersonMapper.class);
        Person person = mapper.selectByPrimaryKey(1);
        System.out.println(person);
    }
}
