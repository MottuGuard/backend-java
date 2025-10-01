package com.mottu.mottuguard;

import com.mottu.mottuguard.repository.UserRepo;
import com.mottu.mottuguard.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MottuguardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MottuguardApplication.class, args);
	}
    @Bean
    CommandLineRunner seedAdmin(UserService users, UserRepo repo){
        return args -> { if(repo.count()==0){ users.register("Admin","admin@mottu.com","admin", true); } };
    }
}
