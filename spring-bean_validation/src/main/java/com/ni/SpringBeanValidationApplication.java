package com.ni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ni")
public class SpringBeanValidationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBeanValidationApplication.class, args);
    }

}
