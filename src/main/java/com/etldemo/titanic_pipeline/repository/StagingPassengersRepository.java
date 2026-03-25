package com.etldemo.titanic_pipeline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etldemo.titanic_pipeline.entity.StagingPassenger;

public interface StagingPassengersRepository extends JpaRepository<StagingPassenger, Long> {
	
	boolean existsByPassengerId(Integer passInteger);
	List<StagingPassenger> findByIsValidTrue();

}
