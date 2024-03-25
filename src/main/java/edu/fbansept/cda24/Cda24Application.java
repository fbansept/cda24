package edu.fbansept.cda24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Cda24Application {

	public static void main(String[] args) {
		SpringApplication.run(Cda24Application.class, args);
	}

	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder () {
		return new BCryptPasswordEncoder();
	}

}
