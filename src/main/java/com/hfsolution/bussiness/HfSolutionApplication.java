package com.hfsolution.bussiness;

import static com.hfsolution.bussiness.feature.user.Enum.Role.ADMIN;
import static com.hfsolution.bussiness.feature.user.Enum.Role.MANAGER;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.hfsolution.bussiness.feature.auth.dto.RegisterRequest;
import com.hfsolution.bussiness.feature.auth.services.AuthenticationService;

@SpringBootApplication
public class HfSolutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(HfSolutionApplication.class, args);
	}

	// @Bean
	// public CommandLineRunner commandLineRunner(
	// 		AuthenticationService service
	// ) {
	// 	return args -> {
	// 		var admin = RegisterRequest.builder()
	// 				.firstname("Admin")
	// 				.lastname("Admin")
	// 				.email("admin@mail.com")
	// 				.password("password")
	// 				.role(ADMIN)
	// 				.build();
	// 		System.out.println("Admin token: " + service.register(admin).getAccessToken());

	// 		var manager = RegisterRequest.builder()
	// 				.firstname("Admin")
	// 				.lastname("Admin")
	// 				.email("manager@mail.com")
	// 				.password("password")
	// 				.role(MANAGER)
	// 				.build();
	// 		System.out.println("Manager token: " + service.register(manager).getAccessToken());

	// 	};
	// }
}
