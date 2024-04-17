package com.liquibase.liquibase.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import com.liquibase.liquibase.House;
import com.liquibase.liquibase.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{
	public List<Item> findAllByOrderByIdAsc();
	

	
}
