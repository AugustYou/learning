package com.felix.activiti;

import org.activiti.engine.RuntimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiLearningApplicationTests {

	@Autowired
	RuntimeService runtimeService;

	@Test
	public void contextLoads() {
		long count = runtimeService.createProcessInstanceQuery()
				.count();
		System.out.println(count);
	}
}
