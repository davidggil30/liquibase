package com.liquibase.liquibase.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.liquibase.liquibase.House;
import com.liquibase.liquibase.Item;
import com.liquibase.liquibase.exceptions.ResourceNotFoundException;
import com.liquibase.liquibase.repositories.HouseRepository;
import com.liquibase.liquibase.repositories.ItemRepository;

@Service
public class LiquibaseService {
	
	@Autowired
	private HouseRepository houseRepository;
	
	@Autowired
	private ItemRepository itemRepository;

	public House createHouse(House house) {
		return houseRepository.save(house);
		
	}
	
	public Item createItem(Item item) {
		return itemRepository.save(item);
	}
	
	
	public List<House> findAllHouses(String owner) {
		List<House> houses = null;
		if(owner == null) {
			houses = houseRepository.findAll();
		} else {
			houses = houseRepository.findByOwnerIgnoreCase(owner);
		}
		return houses;
	}
	
	public Optional<House> findHouseById(int id) {
		return houseRepository.findById(id);
	}
	
//	public List<House> findHouseByOwner(String owner) {
//		return houseRepository.findByOwner(owner);
//	}
	
	public List<Item> findAllItems() {
		return itemRepository.findAll();
	}
	
	public Optional<Item> findItemById(int id) {
		return itemRepository.findById(id);
	}

	public List<House> saveAllHouses(List<House> houses) {
		return houseRepository.saveAll(houses);
	}
	
}
