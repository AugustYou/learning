package top.felixu;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.felixu.config.DataSourceConfig;
import top.felixu.config.MybatisConfig;
import top.felixu.dao.PersonMapper;
import top.felixu.entity.Person;

/**
 * @author felixu
 * @datetime 2018/3/30 10:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, MybatisConfig.class})
public class Test {

    @Autowired
    PersonMapper personMapper;

    @org.junit.Test
    public void select() {
        Person person = personMapper.selectByPrimaryKey(1);
        System.out.println(person.toString());
    }

    @org.junit.Test
    public void insertPerson() {
        Person person = new Person();
        person.setName("felixu");
        person.setAge(26);
        person.setAddress("Mars");
        personMapper.insert(person);
    }
}
