package com.fireBnb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FirebnbApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirebnbApplication.class, args);
	}
@Bean
	public PasswordEncoder encode(){
		return new BCryptPasswordEncoder();

}
}
