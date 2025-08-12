package com.example.data_warehouses_project_server.domain.oltp.repository;

import com.example.data_warehouses_project_server.domain.oltp.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findAllByCreator_Username(String creatorUsername);
}
