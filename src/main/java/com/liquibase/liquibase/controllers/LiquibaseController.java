package com.liquibase.liquibase.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.liquibase.liquibase.House;
import com.liquibase.liquibase.Item;
import com.liquibase.liquibase.exceptions.ResourceNotFoundException;
import com.liquibase.liquibase.services.LiquibaseService;

import ch.qos.logback.core.model.Model;

@RestController
@RequestMapping("/api")
public class LiquibaseController {
	@Autowired
	LiquibaseService service;
	
	public LiquibaseController(LiquibaseService service) {
		this.service = service;
	}
	
	@PostMapping("/houses")
	public List<House> saveAllHouses(@RequestBody List<House> houses) {	
		return service.saveAllHouses(houses);
	}
	
	@GetMapping(value="/houses")
	public List<House> findAllHouses(@RequestParam(name="o") String owner){
		List<House> houses = service.findAllHouses(owner);
		return houses;
	}
	
	@GetMapping(value="/houses/{id}")
	public House getHouseById(@PathVariable int id) {
		return service.findHouseById(id).orElseThrow(()->new ResourceNotFoundException());
	}
	
//	@GetMapping(value="/houses/{owner}")
//	public House getHouseByOwner(@PathVariable String owner) {
//		return service.findHouseByOwner(owner).orElseThrow(()->new ResourceNotFoundException());
//	}
	
	@GetMapping(value="/")
	public String getResquest() {
		return "Hola";
	}
	
	@GetMapping(value="/items")
	public List<Item> findAllItems(){
		List<Item> items = service.findAllItems();
		return items;
	}
	
	@GetMapping(value="/items/{id}")
	public Item getItemById(@PathVariable int id) {
		Item item = service.findItemById(id).orElseThrow(()->new ResourceNotFoundException());
		return item;
	}
	
	@GetMapping(value="/houses/{id}/items")
	public List<Item> getItemsByHouse(@PathVariable int id) {
		return service.findHouseById(id).orElseThrow(()->new ResourceNotFoundException()).getItems();
	}
	
}