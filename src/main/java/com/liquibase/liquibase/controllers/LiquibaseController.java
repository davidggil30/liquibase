package com.liquibase.liquibase.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.liquibase.liquibase.House;
import com.liquibase.liquibase.Item;
import com.liquibase.liquibase.exceptions.ResourceNotFoundException;
import com.liquibase.liquibase.services.LiquibaseService;

@RestController
@RequestMapping("/api")
public class LiquibaseController {
	@Autowired
	LiquibaseService service;
	
	public LiquibaseController(LiquibaseService service) {
		this.service = service;
	}
	
	@PostMapping(value="/houses")
	@ResponseStatus(HttpStatus.CREATED)
	public List<House> saveAllHouses(@RequestBody List<House> houses) {
		return service.saveAllHouses(houses);
	}
	
	@DeleteMapping(value="/houses")
	public void deleteAll() {
		service.deleteAll();
	}
	
	@GetMapping(value="/houses")
	public List<House> findAllHouses(@RequestParam Map<String,String> mapa){
		List<House> houses = service.findAllHouses(mapa.get("owner"));
		return houses;
	}
	
	@GetMapping(value="/houses/{id}")
	public House getHouseById(@PathVariable int id) {
		return service.findHouseById(id).orElseThrow(()->new ResourceNotFoundException());
	}
	
	@GetMapping(value="/")
	public String getResquest() {
		return "Hola";
	}
	
	@GetMapping(value="/items")
	public List<Item> findAllItems(@RequestParam Map<String,String> mapa){
		List<Item> items = service.findAllItems(mapa.get("name"));
		return items;
	}
	
	@GetMapping(value="/items/{id}")
	public Item getItemById(@PathVariable int id) {
		Item item = service.findItemById(id).orElseThrow(()->new ResourceNotFoundException());
		return item;
	}
	
	@GetMapping(value="/houses/{id}/items")
	public List<Item> getItemsByHouse(@PathVariable int id, @RequestParam Map<String,String> mapa) {
		List<Item> items = null;
		if(mapa.containsKey("name")) {
			items = service.findHouseById(id).orElseThrow(()->new ResourceNotFoundException()).getItems().stream().filter(i->mapa.get("name").equalsIgnoreCase(i.getName())).collect(Collectors.toList());
		} else {
			items = service.findHouseById(id).orElseThrow(()->new ResourceNotFoundException()).getItems();
		}
		return items;
			
	}
	
}