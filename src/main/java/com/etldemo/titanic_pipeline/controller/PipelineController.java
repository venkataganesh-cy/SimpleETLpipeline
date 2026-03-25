package com.etldemo.titanic_pipeline.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etldemo.titanic_pipeline.service.PipelineService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/pipeline")
public class PipelineController {

	
	private final PipelineService pipelineService;
	
	@PostMapping("/trigger")
    public ResponseEntity<Map<String, String>> trigger() {
        log.info("Pipeline manually triggered via REST at {}", LocalDateTime.now());
        pipelineService.run();
        return ResponseEntity.ok(Map.of(
            "status", "triggered",
            "timestamp", LocalDateTime.now().toString()
        ));
    }

    // Health check
    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> status() {
        return ResponseEntity.ok(Map.of(
            "status", "running",
            "timestamp", LocalDateTime.now().toString()
        ));
    }
}
