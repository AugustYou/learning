package top.felixu.demo.controller;

import top.felixu.demo.service.IUserService;
import top.felixu.framework.annotation.Autowired;
import top.felixu.framework.annotation.Controller;
import top.felixu.framework.annotation.RequestMapping;
import top.felixu.framework.web.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public ModelAndView speak(HttpServletResponse response) {
        return out(response, userService.speak());
    }

    private ModelAndView out(HttpServletResponse resp, String str){
        try {
            resp.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
