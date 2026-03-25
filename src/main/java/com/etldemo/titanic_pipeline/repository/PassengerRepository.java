package com.etldemo.titanic_pipeline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.etldemo.titanic_pipeline.entity.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

	boolean existsByPassengerId(Integer passengerId);
	
}
