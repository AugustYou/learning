package top.felixu.demo.controller;

import top.felixu.framework.annotation.Controller;
import top.felixu.framework.annotation.RequestMapping;
import top.felixu.framework.annotation.RequestParam;
import top.felixu.framework.web.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author felixu
 * @Date 2018/5/4
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/page")
    public ModelAndView testPage(@RequestParam("name") String name) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>(8);
        map.put("name", name);
        map.put("data", "felixu大帅逼");
        map.put("token", "******************");
        return new ModelAndView("index.html", map);
    }
}
