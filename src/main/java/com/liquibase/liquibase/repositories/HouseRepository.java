package com.liquibase.liquibase.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.liquibase.liquibase.House;

@Repository
public interface HouseRepository extends JpaRepository<House, Integer>{

	public List<House> findByOwnerIgnoreCase(String owner);

}
