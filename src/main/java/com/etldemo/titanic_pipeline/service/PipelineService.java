package com.etldemo.titanic_pipeline.service;


import com.etldemo.titanic_pipeline.model.PassengerRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineService {

    private final CsvReaderService csvReaderService;
    private final StagingService stagingService;
    private final TransformService transformService;

    public void run() {
        try {
            log.info("=== ETL Pipeline starting ===");

            // Phase 1 — Extract
            List<PassengerRecord> records = csvReaderService.readCsv();
            log.info("Phase 1 complete — records extracted: {}", records.size());

            // Phase 2 — Load to staging
            stagingService.loadToStaging(records);
            log.info("Phase 2 complete — staging done");

            // Phase 3 — Transform and load to target
            transformService.transformAndLoad();
            log.info("Phase 3 complete — transform and load done");

            log.info("=== ETL Pipeline finished successfully ===");

        } catch (Exception e) {
            log.error("Pipeline failed: {}", e.getMessage(), e);
        }
    }
}
