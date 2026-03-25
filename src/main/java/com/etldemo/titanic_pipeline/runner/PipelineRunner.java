package com.etldemo.titanic_pipeline.runner;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import com.etldemo.titanic_pipeline.service.PipelineService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class PipelineRunner implements CommandLineRunner{
	private final PipelineService pipelineService;
	
	@Override
	public void run(String... args) throws Exception{
		pipelineService.run();
	}
}
