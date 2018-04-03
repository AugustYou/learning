package top.felixu.v1;

import lombok.Data;
import lombok.ToString;

/**
 * @author felixu
 * @datetime 2018/4/1 22:56
 */
@ToString
@Data
public class Person {
    private Integer id;

    private String address;

    private Integer age;

    private String name;
}