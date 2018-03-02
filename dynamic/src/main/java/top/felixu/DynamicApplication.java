package top.felixu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.felixu.datasource.DynamicDataSourceRegister;
import top.felixu.entity.User;
import top.felixu.service.IUserService;

import java.util.List;

@SpringBootApplication
@MapperScan("top.felixu.mapper")
@RestController
@RequestMapping("/user")
@Import(DynamicDataSourceRegister.class)
public class DynamicApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicApplication.class, args);
	}

	@Autowired
    IUserService userService;

	@GetMapping("/test")
	public List<User> getUser() {
//	    return userService.getOneUsers();
	    return userService.getTwoUsers();
    }
}
