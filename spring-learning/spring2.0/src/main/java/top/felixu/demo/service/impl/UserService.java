package top.felixu.demo.service.impl;

import top.felixu.framework.annotation.Service;
import top.felixu.demo.service.IUserService;

/**
 * @Author felixu
 * @Date 2018/4/23
 */
@Service
public class UserService implements IUserService {

    @Override
    public String speak() {
        return "felixu是个大帅逼";
    }
}
