package top.felixu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.felixu.annotation.MyAnnotation;
import top.felixu.service.TestService;

@SpringBootApplication
@RestController
public class BootAopApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootAopApplication.class, args);
	}

	@MyAnnotation(name = "felixu")
    @GetMapping("/test1")
	public void test1() {
        System.out.println("test1");
    }

    @GetMapping("/test2")
    public void test2() {
        System.out.println("test2");
    }

    @Autowired
    TestService testService;

	@GetMapping("/test3")
	public void test3() {
	    testService.test();
        System.out.println("test3");
    }
}
