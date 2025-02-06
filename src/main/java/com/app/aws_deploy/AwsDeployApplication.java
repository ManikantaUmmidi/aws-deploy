package com.app.aws_deploy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AwsDeployApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsDeployApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello() {
		return "Welcome to aws deploy using ci/cd pipeline";
	}
}
