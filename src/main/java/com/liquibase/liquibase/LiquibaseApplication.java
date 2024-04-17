package com.liquibase.liquibase;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.liquibase.liquibase.services.LiquibaseService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class LiquibaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiquibaseApplication.class, args);
	}
	
	@Autowired
	LiquibaseService service; 

}
