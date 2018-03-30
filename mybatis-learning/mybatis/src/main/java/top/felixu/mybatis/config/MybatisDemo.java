package top.felixu.mybatis.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import top.felixu.dao.PersonMapper;
import top.felixu.dao.UserMapper;
import top.felixu.entity.Person;
import top.felixu.entity.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MybatisDemo {

    public static void main(String[] args) {
        List<String> strs = new ArrayList<>();
        strs.add("1");
        strs.add("2");
        strs.stream().forEach(s -> System.out.println(s));
    }

//    public static void main(String[] args) throws Exception {
//        SqlSession sqlSession = getSqlSession();
////        Person person = new Person();
////        person.setId(6);
////        person.setName("felixu");
////        person.setAddress("Mars");
////        person.setAge(26);
////
////        insert(sqlSession, person);
////        sqlSession.commit();
////        sqlSession.close();
//        System.out.println(getPerson(sqlSession, 1).toString());
//    }

    private static SqlSession getSqlSession() throws Exception{
        String resource = "/Users/felixu/github/learning/mybatis-learning/mybatis/src/main/java/top/felixu/mybatis/config/mybatis-config.xml";
        InputStream inputStream = new FileInputStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession(false);
    }

    private static int insertPerson(SqlSession sqlSession, Person person) {
        PersonMapper mapper = sqlSession.getMapper(PersonMapper.class);
        return mapper.insert(person);
    }

    private static Person getPerson(SqlSession sqlSession, int id) {
        PersonMapper mapper = sqlSession.getMapper(PersonMapper.class);
        return mapper.selectByPrimaryKey(id);
    }

    private static int insertUser(SqlSession sqlSession, User user) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.insert(user);
    }

//    public static void main(String[] args) throws Exception {
//        SqlSession sqlSession = getSqlSession();
//        User user = new User();
//        user.setName("felixu");
//        insertUser(sqlSession, user);
//        sqlSession.commit();
//        sqlSession.close();
//    }
}
