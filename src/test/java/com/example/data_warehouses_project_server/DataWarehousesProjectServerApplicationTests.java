package com.example.data_warehouses_project_server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class DataWarehousesProjectServerApplicationTests {

	@Test
	void contextLoads() {
	}

}
