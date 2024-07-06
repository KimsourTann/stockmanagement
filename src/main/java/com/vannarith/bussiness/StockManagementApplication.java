package com.vannarith.bussiness;

import static com.vannarith.bussiness.user.Role.ADMIN;
import static com.vannarith.bussiness.user.Role.MANAGER;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.vannarith.bussiness.auth.AuthenticationService;
import com.vannarith.bussiness.auth.RegisterRequest;
import com.vannarith.bussiness.user.Role;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class StockManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockManagementApplication.class, args);
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
