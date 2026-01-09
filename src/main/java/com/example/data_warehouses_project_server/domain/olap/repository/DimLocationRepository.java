package com.example.data_warehouses_project_server.domain.olap.repository;

import com.example.data_warehouses_project_server.domain.olap.entity.DimLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimLocationRepository extends JpaRepository<DimLocationEntity, Long> {
}
