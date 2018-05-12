package top.felixu.doc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @Author felixu
 * @Date 2018/5/11
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public Object test() {
        return "this is test";
    }
}
