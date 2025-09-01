package br.com.lkm.taxone.mapper;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class TaxONEMapperApp {

	public static void main(String... args) {
		String properties = String.valueOf(System.getProperties());
		log.info(properties);
		log.info("currentDir:" + new File(".").getAbsolutePath());
		SpringApplication.run(TaxONEMapperApp.class);
	}
	
}
