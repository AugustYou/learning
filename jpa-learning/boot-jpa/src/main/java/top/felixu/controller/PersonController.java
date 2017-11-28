package top.felixu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.felixu.service.PersonService;

/**
 * @author: felix.
 * @createTime: 2017/11/28.
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

//    @GetMapping("/{address}")
//    public Object getPersonByAddress(@PathVariable("address") String address) {
//        return personService.getPersonByAddress(address);
//    }

    @GetMapping("/{name}")
    public Object getPersonByName(@PathVariable("name") String name) {
        return personService.getPersonByName(name);
    }

    @PostMapping("")
    public Object addPerson(@RequestParam String name, @RequestParam String address, @RequestParam Integer age) {
        return personService.addPerson(name, address, age);
    }
}
