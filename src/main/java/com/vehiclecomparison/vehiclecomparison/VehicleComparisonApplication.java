package com.vehiclecomparison.vehiclecomparison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.vehiclecomparison.vehiclecomparison"})
public class VehicleComparisonApplication {

	public static void main(String[] args) {
		System.setProperty("server.port", "8085");
		SpringApplication.run(VehicleComparisonApplication.class, args);
	}

}
