package com.logistics.checklist;

import javax.annotation.Resource;

import com.logistics.checklist.services.AdminService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ChecklistApplication {
	@Resource
	AdminService storageService;

	public static void main(final String[] args) {
		SpringApplication.run(ChecklistApplication.class, args);
	}
	
	public void run(final String... arg) throws Exception {
		storageService.init();
	}

}
