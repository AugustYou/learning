package top.felixu.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * @author : felixu
 * @createTime : 2017/11/27.
 */
@Table(name = "person")
@Entity
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
    @Column(name = "address")
    private String address;
}
