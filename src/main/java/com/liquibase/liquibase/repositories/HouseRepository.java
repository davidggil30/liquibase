package com.liquibase.liquibase.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import com.liquibase.liquibase.House;
import com.liquibase.liquibase.Item;

@Repository
public interface HouseRepository extends JpaRepository<House, Integer>{
	public List<House> findAllByOrderByIdAsc();

	public List<House> findByOwner(String owner);
	
	
	
}
