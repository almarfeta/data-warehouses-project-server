package com.example.data_warehouses_project_server.domain.olap.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.data_warehouses_project_server.domain.olap.entity.DimTimeEntity;

@Repository
public interface DimTimeRepository extends JpaRepository<DimTimeEntity, Long> {

    boolean existsByDateValue(LocalDate dateValue);

    Optional<DimTimeEntity> findByDateValue(LocalDate dateValue);
}
