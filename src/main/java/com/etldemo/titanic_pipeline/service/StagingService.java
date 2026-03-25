package com.etldemo.titanic_pipeline.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.etldemo.titanic_pipeline.entity.StagingPassenger;
import com.etldemo.titanic_pipeline.model.PassengerRecord;
import com.etldemo.titanic_pipeline.repository.StagingPassengersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StagingService {

	private final StagingPassengersRepository stagingPassengersRepository;
	
	public void loadToStaging(List<PassengerRecord> records) {
		int inserted =0;
		int skipped =0;
		
		for(PassengerRecord r: records) {
			if(stagingPassengersRepository.existsByPassengerId(r.getPassengerId())) {
				skipped++;
				continue;
			}
			
			StagingPassenger entity = new StagingPassenger();
			entity.setPassengerId(r.getPassengerId());
            entity.setSurvived(r.getSurvived());
            entity.setPclass(r.getPClass());
            entity.setName(r.getName());
            entity.setSex(r.getSex());
            entity.setAge(r.getAge());
            entity.setSibSp(r.getSibSp());
            entity.setParch(r.getParch());
            entity.setTicket(r.getTicket());
            entity.setFare(r.getFare());
            entity.setCabin(r.getCabin());
            entity.setEmbarked(r.getEmbarked());
            entity.setLoadedAt(LocalDateTime.now());
            entity.setIsValid(validateRecord(r));
            
            stagingPassengersRepository.save(entity);
            inserted++;
		}
		
		log.info("Staging complete — inserted: {}, skipped (duplicates): {}", inserted, skipped);
	}

	private Boolean validateRecord(PassengerRecord r) {
		
		return r.getPassengerId() != null
	            && r.getSurvived() != null && !r.getSurvived().isBlank()
	            && r.getPClass() != null && !r.getPClass().isBlank()
	            && r.getFare() != null && !r.getFare().isBlank();
	}
	
}
