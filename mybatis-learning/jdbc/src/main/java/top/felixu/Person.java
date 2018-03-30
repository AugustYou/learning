package top.felixu;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    public Integer id;
    public String name;
    public int age;
    public String address;

    public Person(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }
}
