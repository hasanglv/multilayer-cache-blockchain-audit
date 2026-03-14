package com.hasan.thesisProject;

import com.hasan.thesisProject.service.BlockchainService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ThesisProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThesisProjectApplication.class, args);
	}
}