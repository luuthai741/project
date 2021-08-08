package com.project.app;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import com.project.service.UserService;

@Configuration
@SpringBootApplication(scanBasePackages = { "com.project" })
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class BookProjectApplication {
@Autowired
private UserService userSerivce;
	public static void main(String[] args) {
		SpringApplication.run(BookProjectApplication.class, args);
	}
	
//	@PostConstruct void init(){
//		userSerivce.deleteByID(13);
//	}
}
