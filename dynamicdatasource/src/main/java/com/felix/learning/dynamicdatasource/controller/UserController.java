package com.felix.learning.dynamicdatasource.controller;

import com.felix.learning.dynamicdatasource.controller.dto.RespDTO;
import com.felix.learning.dynamicdatasource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xufei.
 * @createTime: 2017/7/11.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public RespDTO getUser() {
        return RespDTO.onSuc(userService.getUser());
    }
}
