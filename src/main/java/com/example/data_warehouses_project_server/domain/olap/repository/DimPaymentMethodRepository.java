package com.example.data_warehouses_project_server.domain.olap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.data_warehouses_project_server.domain.olap.entity.DimPaymentMethodEntity;

@Repository
public interface DimPaymentMethodRepository extends JpaRepository<DimPaymentMethodEntity, Long> {

    boolean existsByPaymentMethod(String paymentMethod);

    Optional<DimPaymentMethodEntity> findByPaymentMethod(String paymentMethod);
}
