package com.felix.learning.dynamicdatasource.service;

import com.felix.learning.dynamicdatasource.dao.UserMapper;
import com.felix.learning.dynamicdatasource.datasource.TargetDataSource;
import com.felix.learning.dynamicdatasource.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author: xufei.
 * @createTime: 2017/7/11.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

     @TargetDataSource(name = "ds1")
    public List<User> getUser() {
        return userMapper.getUsers();
    }
}
