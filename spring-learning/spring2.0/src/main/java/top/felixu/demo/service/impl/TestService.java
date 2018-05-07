package top.felixu.demo.service.impl;

import top.felixu.demo.service.ITestService;
import top.felixu.framework.annotation.Service;

/**
 * @Author felixu
 * @Date 2018/5/7
 */
@Service
public class TestService implements ITestService {


    @Override
    public String test() {
        return "我是大帅逼！！！";
    }
}
