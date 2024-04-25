package com.liquibase.liquibase.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liquibase.House;
import com.liquibase.Item;
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
	
	public Optional<Item> findItemById(int id) {
		return itemRepository.findById(id);
	}

	public List<House> saveAllHouses(List<House> houses) {
		houses.stream().filter(h->!h.getItems().isEmpty()).forEach(h->h.getItems().forEach(i->i.setHouse(h)));
		return houseRepository.saveAll(houses);
	}

	public List<Item> findAllItems(String name) {
		List<Item> items = null;
		if(name == null) {
			items = itemRepository.findAll();
		} else {
			items = itemRepository.findByNameIgnoreCase(name);
		}
		return items;
	}

	public void deleteAll() {
		houseRepository.deleteAll();
	}

	
}
