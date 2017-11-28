package top.felixu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.felixu.entity.Person;

import java.util.List;

/**
 * @author : felixu
 * @createTime : 2017/11/27.
 */
public interface PersonRepository extends JpaRepository<Person, Integer> {

    /**
     * 通过地址查询
     * @param address
     * @return
     */
    List<Person> findByAddress(String address);

    /**
     * 根据名字查询
     * @param name
     * @return
     */
    String findByName(String name);
}
