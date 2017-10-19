package com.racek;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.racek.service.StorageServiceTemplate;

@SpringBootApplication
public class TestFileuploadAppApplication implements CommandLineRunner {

	@Resource
	StorageServiceTemplate storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(TestFileuploadAppApplication.class, args);
	}
	
	@Override
	public void run(String... arg) throws Exception {
		storageService.deleteAll();
		storageService.init();
	}
}
