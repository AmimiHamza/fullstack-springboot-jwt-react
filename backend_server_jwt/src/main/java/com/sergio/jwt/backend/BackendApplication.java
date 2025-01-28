package com.sergio.jwt.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sergio.jwt.backend.entites.User;
import com.sergio.jwt.backend.repositories.UserRepository;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	

	// @Bean
    // CommandLineRunner commandLineRunner(UserRepository userRepository){
    //     return args -> {
    //         System.out.println("accounts are already in the database");
    //         userRepository.save(new User(null,"hamza","amimi","hamimi@insea.ac.ma","hamza123"));
    //     };
    // }

}
