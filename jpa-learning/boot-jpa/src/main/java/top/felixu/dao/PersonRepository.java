package top.felixu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.felixu.entity.Person;

import java.util.List;

/**
 * @author : felixu
 * @createTime : 2017/11/27.
 */
public interface PersonRepository extends JpaRepository<Person, Integer> {

//    List<Person>
}
