package com.etldemo.titanic_pipeline.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.etldemo.titanic_pipeline.entity.Passenger;
import com.etldemo.titanic_pipeline.entity.StagingPassenger;
import com.etldemo.titanic_pipeline.repository.PassengerRepository;
import com.etldemo.titanic_pipeline.repository.StagingPassengersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransformService {

	private final StagingPassengersRepository stagingRepo;
	private final PassengerRepository passengerRepo;
	
	public void transformAndLoad() {
		
		List<StagingPassenger> validRows = stagingRepo.findByIsValidTrue();
		
		int inserted =0;
		int skipped =0;
		int failed =0;
		
		
		for (StagingPassenger raw: validRows) {
			if(passengerRepo.existsByPassengerId(raw.getPassengerId())) {
				skipped++;
				continue;
			}
			
			try {
				Passenger p = new Passenger();
				
				p.setPassengerId(raw.getPassengerId());
				p.setSurvived("1".equals(raw.getSurvived()));
				p.setPclass(Integer.parseInt(raw.getPclass()));
				p.setName(raw.getName());
				p.setSex(raw.getSex());
				p.setAge(parseDecimal(raw.getAge()));
				p.setSibSp(parseInt(raw.getSibSp()));
				p.setTicket(raw.getTicket());
				p.setParch(parseInt(raw.getParch()));
				p.setFare(parseDecimal(raw.getFare()));
				p.setCabin(raw.getCabin().isBlank() ? null : raw.getCabin());
                p.setEmbarked(raw.getEmbarked().isBlank() ? null : raw.getEmbarked());
                p.setAgeGroup(deriveAgeGroup(p.getAge()));
                p.setTransformedAt(LocalDateTime.now());
                
                passengerRepo.save(p);
                inserted++;
			}catch(Exception e) {
				log.warn("Failed to transform passenger #+{}: {}", raw.getPassengerId(), e.getMessage());
				failed++;
			}
		}
		log.info("Transform complete — inserted: {}, skipped: {}, failed: {}", inserted, skipped, failed);
	}

	private String deriveAgeGroup(BigDecimal age) {
		if (age == null) return "Unknown";
        int a = age.intValue();
        if (a < 18)  return "Child";
        if (a < 60)  return "Adult";
        return "Senior";
	}

	private Integer parseInt(String value) {
		if (value == null || value.isBlank()) return null;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
	}

	private BigDecimal parseDecimal(String value) {
		 if (value == null || value.isBlank()) return null;
	        try {
	            return new BigDecimal(value.trim());
	        } catch (NumberFormatException e) {
	            return null;
	        }
	}
	
	
}
