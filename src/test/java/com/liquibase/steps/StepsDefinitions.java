package com.liquibase.steps;

import java.util.ArrayList;
import java.util.List;

import com.liquibase.House;
import com.liquibase.Item;
import com.liquibase.TestContainers;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class StepsDefinitions extends TestContainers{
	
	@Before
	public static void beforeAll() {
		container.start();
	}
	
	@After
	public static void afterAll() {
		container.stop();
	}
	
	@Given("Una casa de owner {string} y fullypaid igual a {string} con {int} item de nombre {string}")
    public void firstStep(String owner, String fullypaid, int numItem, String name) {
		House house1 = new House();
		house1.setOwner(owner);	
		house1.setFullypaid(Boolean.valueOf(fullypaid));
		ArrayList<House> houses = new ArrayList<House>();
		houses.addAll(List.of(house1));
		
		Item item1 = new Item();
		item1.setId(numItem);
		item1.setName(name);
		List<Item> items = new ArrayList<Item>();
		items.addAll(List.of(item1));
		house1.setItems(items);
		
		callEndpoints("/houses", "post", houses);
		
		System.out.println("first step");
    }
	
	@When("Cuando llamo al endpoint {string} y verbo {string}")
	public void secondStep(String endpoint, String verbo) {
		
		System.out.println("second step");
	}
	
	@Then("Obtengo como respuesta el código \"201\"")
	public void thirdStep() {
		System.out.println("third step");
	}
	
}