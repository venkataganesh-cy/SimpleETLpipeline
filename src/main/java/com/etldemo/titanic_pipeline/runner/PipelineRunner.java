package com.etldemo.titanic_pipeline.runner;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.etldemo.titanic_pipeline.model.PassengerRecord;
import com.etldemo.titanic_pipeline.service.CsvReaderService;
import com.etldemo.titanic_pipeline.service.StagingService;
import com.etldemo.titanic_pipeline.service.TransformService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class PipelineRunner implements CommandLineRunner{
	private final CsvReaderService csvReaderService;
	private final StagingService stagingService;
	private final TransformService transformService;

    @Override
    public void run(String... args) throws Exception {
        log.info("=== ETL Pipeline starting ===");
        
//        phase 1 - Extract
        List<PassengerRecord> records = csvReaderService.readCsv();
        log.info("Records extracted: {}", records.size());

//        records.stream().limit(5).forEach(r ->
//            log.info("Passenger #{}: {} | Age: {} | Survived: {}",
//                r.getPassengerId(), r.getName(), r.getAge(), r.getSurvived())
//        );
        
        // phase 2 -- load to staging 
        stagingService.loadToStaging(records);
        
        // phase 3 -- transform and load to target
        transformService.transformAndLoad();

        log.info("=== Pipeline run completed ===");
    }
}
