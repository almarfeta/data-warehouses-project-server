package com.example.data_warehouses_project_server.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProductRepository extends JpaRepository<Product, Long> {
}
