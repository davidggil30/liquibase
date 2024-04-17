package com.liquibase.liquibase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestHeadersUriSpec;
import org.springframework.web.client.RestClient.ResponseSpec;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import com.liquibase.liquibase.exceptions.ResourceNotFoundException;
import com.liquibase.liquibase.repositories.HouseRepository;
import com.liquibase.liquibase.repositories.ItemRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class TestContainers {
	
	

	@Autowired
	private HouseRepository houseRepository;

	@LocalServerPort
	private int port;
	
	@Value("${REMOTE_BASE_URI:http://localhost}")
	String baseURI;

	RestClient restClient() {
	  return RestClient.create(baseURI + ":" + port + "/api");
	}
	
    static DockerImageName postgres = DockerImageName.parse("postgres:latest");

	@Container
	static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(postgres)
		.withDatabaseName("BASIC_TEST")
		.withUsername("postgres")
		.withPassword("postgres")
		.withReuse(true)
		.withCopyFileToContainer(
				MountableFile.forClasspathResource(
						"db/migration/"), "/docker-entrypoint-initdb.d/");
	
	@BeforeAll
	public static void beforeAll() {
		container.start();
	}

	//crear en controller
	@BeforeEach
	public void Before() {		
		House house1 = new House();
		house1.setOwner("David");
		house1.setFullypaid(true);
		House house2 = new House();
		house2.setOwner("Pedro");
		house2.setFullypaid(false);
		ArrayList<House> houses = new ArrayList<House>();
		houses.addAll(List.of(house1, house2));
		
		Item item1 = new Item("item1");
		Item item2 = new Item("item2");
		ArrayList<Item> items = new ArrayList<Item>();
		items.addAll(List.of(item1, item2));
		house1.setItems(items);
		
		ResponseEntity<Void> result = restClient().post() 
				  .uri("/houses")
				  .contentType(MediaType.APPLICATION_JSON)
				  .body(houses)
				  .retrieve()
				  .toBodilessEntity(); 
	}
	
	@AfterAll
	public static void afterAll() {
		container.stop();
	}
	
	@DynamicPropertySource
	public static void configureProperties(DynamicPropertyRegistry registry) {
	    registry.add("spring.datasource.url", container::getJdbcUrl);
	    registry.add("spring.datasource.username", container::getUsername);
	    registry.add("spring.datasource.password", container::getPassword);
	}
	
//	//@Test
//	public void prueba1(){
//		assertEquals(2, houseRepository.findAll().size());
//	}
//	
//	//@Test()
//	public void prueba2() {
//		List<House> houses = houseRepository.findAll();
//		assertEquals("David", houses.stream().filter(house->house.getOwner().contentEquals("David")).collect(Collectors.toList()).get(0).getOwner());
//		assertEquals(2,houses.stream().filter(house->!house.getItems().isEmpty()).map(House::getItems).collect(Collectors.toList()).size());
//	}
//	
//	//@Test
//	public void prueba3() {
//		List<House> houses = houseRepository.findAll();
//		assertEquals(houses.stream().filter(house->house.getOwner().contentEquals("Ricardo")).collect(Collectors.toList()).size(), 0);
//	}
	
	@Test
	public void prueba1Client() {
		List<House> houses = restClient().get() 
				  .uri("/houses") 
				  .retrieve() 
				  .body(new ParameterizedTypeReference<List<House>>(){}); 
		assertEquals(2, houses.size());
	}
	
	//@Test
	public void prueba2Client() { 
		assertThrows(RuntimeException.class, ()->restClient().get()
				  .uri("/houses/3")
				  .retrieve()
				  .body(new ParameterizedTypeReference<List<House>>(){}));
	}
	
	//@Test
	public void prueba3Test() {
		List<House> houses = restClient().get() 
				  .uri("/houses") 
				  .retrieve()
				  .body(new ParameterizedTypeReference<List<House>>(){}); 
		assertEquals("David", houses.stream().filter(house->house.getOwner().contentEquals("David")).collect(Collectors.toList()).get(0).getOwner());
		assertEquals(2,houses.stream().filter(house->!house.getItems().isEmpty()).map(House::getItems).collect(Collectors.toList()).size());
	}
	
	//@Test
	public void prueba4Client() {
		List<House> houses = restClient().get() 
				  .uri("/houses") 
				  .retrieve() 
				  .body(new ParameterizedTypeReference<List<House>>(){});
		assertEquals(houses.stream().filter(house->house.getOwner().contentEquals("Ricardo")).collect(Collectors.toList()).size(), 0);

	}

	
	
}
