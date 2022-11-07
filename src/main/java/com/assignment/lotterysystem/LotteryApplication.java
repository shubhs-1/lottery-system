package com.assignment.lotterysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Shubham Kalaria
 * Entry point for lottery system application
 */
@SpringBootApplication
@EnableScheduling
public class LotteryApplication {

	/**
	 * Main method to initiate the application execution
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(LotteryApplication.class, args);
	}

}
