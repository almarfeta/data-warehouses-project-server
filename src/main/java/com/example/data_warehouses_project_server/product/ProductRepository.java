package com.example.data_warehouses_project_server.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCreator_Username(String creatorUsername);
}
