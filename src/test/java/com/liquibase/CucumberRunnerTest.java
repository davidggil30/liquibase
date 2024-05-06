package com.liquibase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

import io.cucumber.junit.platform.engine.Cucumber;

@Cucumber
public class CucumberRunnerTest {
	
	@LocalServerPort
	private int port;
	
	@Value("${remote.base.uri}")
	String baseURI;

	RestClient restClient() {
	  return RestClient.create(baseURI + ":" + port + "/api");
	} 
	
}