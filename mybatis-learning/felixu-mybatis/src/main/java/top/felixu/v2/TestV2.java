package top.felixu.v2;

import top.felixu.v1.Person;
import top.felixu.v1.PersonMapper;
import top.felixu.v2.configuration.Configuration;
import top.felixu.v2.executor.SimpleExecutor;
import top.felixu.v2.sqlsession.SqlSession;

/**
 * @author felixu
 * @datetime 2018/4/3 06:37
 */
public class TestV2 {

    public static void main(String[] args) {
        Configuration configuration = new Configuration().mapperScan("top.felixu.v1").build();
        SqlSession sqlSession = new SqlSession(configuration, new SimpleExecutor());
        PersonMapper mapper = sqlSession.getMapper(PersonMapper.class);
        Person person = mapper.selectByPrimaryKey(1);
        System.out.println(person);
    }
}
