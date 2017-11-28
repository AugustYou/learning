package top.felixu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.felixu.dao.PersonRepository;
import top.felixu.entity.Person;
import java.util.List;

/**
 * @author: felix.
 * @createTime: 2017/11/28.
 */
@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public List<Person> getPersonByAddress(String address) {
        return personRepository.findByAddress(address);
    }

    public String getPersonByName(String name) {
        return personRepository.findByName(name);
    }

    public Object addPerson(String name, String address, Integer age) {
        Person person = new Person();
        person.setName(name);
        person.setAddress(address);
        person.setAge(age);
        return personRepository.save(person);
    }
}