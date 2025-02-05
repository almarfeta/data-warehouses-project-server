package com.example.data_warehouses_project_server;

import org.springframework.boot.SpringApplication;

public class TestDataWarehousesProjectServerApplication {

	public static void main(String[] args) {
		SpringApplication.from(DataWarehousesProjectServerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
