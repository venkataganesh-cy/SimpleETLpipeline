package com.etldemo.titanic_pipeline.runner;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.etldemo.titanic_pipeline.model.PassengerRecord;
import com.etldemo.titanic_pipeline.service.CsvReaderService;
import com.etldemo.titanic_pipeline.service.StagingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class PipelineRunner implements CommandLineRunner{
	private final CsvReaderService csvReaderService;
	private final StagingService stagingService;

    @Override
    public void run(String... args) throws Exception {
        log.info("=== ETL Pipeline starting ===");
        List<PassengerRecord> records = csvReaderService.readCsv();
        log.info("Total records read: {}", records.size());

//        records.stream().limit(5).forEach(r ->
//            log.info("Passenger #{}: {} | Age: {} | Survived: {}",
//                r.getPassengerId(), r.getName(), r.getAge(), r.getSurvived())
//        );
        
        stagingService.loadToStaging(records);

        log.info("=== Extract phase complete ===");
    }
}
