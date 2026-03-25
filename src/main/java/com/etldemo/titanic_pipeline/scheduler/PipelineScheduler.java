package com.etldemo.titanic_pipeline.scheduler;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.etldemo.titanic_pipeline.service.PipelineService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component	
@RequiredArgsConstructor
public class PipelineScheduler {
	
	private final PipelineService pipelineService;
	
	@Scheduled(fixedRate = 60000)
	public void scheduleRun() {
		log.info("Scheduler triggered at {}", LocalDateTime.now());
		pipelineService.run();
	}

}
