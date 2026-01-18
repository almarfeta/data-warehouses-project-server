package com.example.data_warehouses_project_server.domain.olap.repository;

import com.example.data_warehouses_project_server.domain.olap.entity.DimProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DimProductRepository extends JpaRepository<DimProductEntity, Long> {

    Optional<DimProductEntity> findByProductId(Long productId);
}
