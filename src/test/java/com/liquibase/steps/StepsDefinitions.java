package com.liquibase.steps;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.liquibase.House;
import com.liquibase.Item;
import com.liquibase.TestContainers;
import com.liquibase.cucumber.ScenarioContext;
import com.liquibase.cucumber.TestContext;

import io.cucumber.java.AfterAll;
import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepsDefinitions extends TestContainers{
	
	@Autowired
	TestContext testContext;
	
	@BeforeAll
	public static void beforeAll() {
		container.start();
	}
	
	@AfterAll
	public static void afterAll() {
		container.stop();
	}
	
	@After
	public void cleanUp() {
		callEndpoints("/houses", "delete", null, null);
	}
	
	@Given ("Una casa de owner {string} y fullypaid igual a {string} con {int} item de name {string}")
    public void firstStep(String owner, String fullypaid, int numItem, String name) {
		ArrayList<House> houses = new ArrayList<House>();
		House house = new House();
		house.setOwner(owner);	
		house.setFullypaid(Boolean.valueOf(fullypaid));
		houses.addAll(List.of(house));
		
		Item item = new Item();
		item.setName(name);
		ArrayList<Item> items = new ArrayList<Item>();
		items.addAll(List.of(item));
		house.setItems(items);
		
		callEndpoints("/houses", "post", houses, null);
		
		System.out.println("Given 1");
    }
	
	@When ("Cuando llamo al endpoint {string} y verbo {string}")
	public void then1Scenario(String endpoint, String verbo) {
		ScenarioContext scenarioContext = testContext.getScenarioContext();
		ResponseEntity<List<House>> response = (ResponseEntity<List<House>>) callEndpoints(endpoint, verbo, null, null);
		scenarioContext.setProperty("httpStatus", response.getStatusCode());
		System.out.println("When Scenario 1");
	}
	
	@When ("Cuando llamo al endpoint {string} y verbo {string} y owner {string}")
	public void then2Scenario(String endpoint, String verbo, String owner) {
		ScenarioContext scenarioContext = testContext.getScenarioContext();
		ResponseEntity<List<House>> response = (ResponseEntity<List<House>>) callEndpoints(endpoint, verbo, null, owner);
		scenarioContext.setProperty("houses", response.getBody());
		scenarioContext.setProperty("httpStatus", response.getStatusCode());
		System.out.println("When Scenario 2");
	}
	
	@Then ("Obtengo como respuesta el código {int}")
	public void then1Scenario(int codigo) {
		ScenarioContext scenarioContext = testContext.getScenarioContext();
		int httpStatus = ((HttpStatusCode) scenarioContext.getProperty("httpStatus")).value();
		assertEquals(codigo, httpStatus);
		System.out.println("Then Scenario 1");
	}
	
	@Then ("Obtengo como respuesta la casa con 1 item de name {string}")
	public void then2Scenario(String name) {
		ScenarioContext scenarioContext = testContext.getScenarioContext();
		List<House> houses = (List<House>) scenarioContext.getProperty("houses");
		assertEquals(true, houses.stream().filter(house->house.getItems().stream().filter(item->item.getName().equals(name)).findAny().isPresent()).findAny().isPresent());
		System.out.println("Then 1 Scenario 2");
	}
	
	@Then ("El número de casas es {int}")
	public void then2Scenario2(int numHouses){
		ScenarioContext scenarioContext = testContext.getScenarioContext();
		List<House> houses = (List<House>) scenarioContext.getProperty("houses");
		assertEquals(numHouses, houses.size());
		System.out.println("Then 2 Scenario 2");
	}
	
}