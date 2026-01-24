package com.example.data_warehouses_project_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class DataWarehousesProjectServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataWarehousesProjectServerApplication.class, args);
	}

}
