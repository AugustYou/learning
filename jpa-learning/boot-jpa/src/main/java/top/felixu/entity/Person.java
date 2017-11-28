package top.felixu.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * @author : felixu
 * @createTime : 2017/11/27.
 */
@Table(name = "person")
@Entity
@Data
@NamedQuery(name = "Person.findByName", query = "select p.name from Person p where p.name = ?1")
public class Person {

    @Id
    @GeneratedValue(generator = "snowFlakeId")
    @GenericGenerator(name = "snowFlakeId", strategy = "top.felixu.idworker.SnowflakeId")
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
    @Column(name = "address")
    private String address;
}
