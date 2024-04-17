package com.liquibase.liquibase.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.liquibase.liquibase.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{

	public List<Item> findByNameIgnoreCase(String name);
	
}
