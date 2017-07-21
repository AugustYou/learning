package com.felix.learning.dynamicdatasource;

import com.felix.learning.dynamicdatasource.datasource.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({DynamicDataSourceRegister.class})
@MapperScan("com.felix.learning.dynamicdatasource")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
