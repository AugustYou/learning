package top.felixu.doc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author felixu
 * @Date 2018/5/11
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        return "/index";
    }
}
