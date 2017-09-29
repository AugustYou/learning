package com.felix.activiti;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.felix.activiti.dao")
public class ActivitiLearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiLearningApplication.class, args);
	}
}