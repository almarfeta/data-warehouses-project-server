package com.example.data_warehouses_project_server.domain.oltp.repository;

import com.example.data_warehouses_project_server.domain.oltp.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    Optional<BrandEntity> findByBrandName(String brandName);
}
