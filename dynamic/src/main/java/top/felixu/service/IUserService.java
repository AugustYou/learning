package top.felixu.service;

import top.felixu.entity.User;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author felixu
 * @since 2018-01-23
 */
public interface IUserService extends IService<User> {
    List<User> getOneUsers();
    List<User> getTwoUsers();
}
