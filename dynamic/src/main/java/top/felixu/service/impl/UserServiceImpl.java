package top.felixu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import top.felixu.datasource.TargetDataSource;
import top.felixu.entity.User;
import top.felixu.mapper.UserMapper;
import top.felixu.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author felixu
 * @since 2018-01-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public List<User> getOneUsers() {
        return selectList(Condition.create());
    }

    @TargetDataSource(name = "ds1")
    @Override
    public List<User> getTwoUsers() {
        return selectList(Condition.create());
    }
}
