package com.Extract.application;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Extract.reader.ExtractBatchConfiguration;

@SpringBootApplication
@EnableBatchProcessing
//@RestController
@ComponentScan
public class Application {

	/*@RequestMapping(value = "/one")
	public ExtractBatchConfiguration extractController() {
		ExtractBatchConfiguration extractBatchConfiguration = new ExtractBatchConfiguration();
		extractBatchConfiguration.claimExtractJob();
		return extractBatchConfiguration;
	}*/
	
	public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
	
}
