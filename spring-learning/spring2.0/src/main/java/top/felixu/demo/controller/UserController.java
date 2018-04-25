package top.felixu.demo.controller;

import top.felixu.annotation.Autowired;
import top.felixu.annotation.Controller;
import top.felixu.annotation.RequestMapping;
import top.felixu.demo.service.IUserService;

/**
 * @Author felixu
 * @Date 2018/4/23
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping("/speak")
    public Object speak() {
        return userService.speak();
    }
}
