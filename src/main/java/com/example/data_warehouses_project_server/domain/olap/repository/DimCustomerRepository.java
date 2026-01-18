package com.example.data_warehouses_project_server.domain.olap.repository;

import com.example.data_warehouses_project_server.domain.olap.entity.DimCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DimCustomerRepository extends JpaRepository<DimCustomerEntity, Long> {

    Optional<DimCustomerEntity> findByCustomerId(Long customerId);
}
