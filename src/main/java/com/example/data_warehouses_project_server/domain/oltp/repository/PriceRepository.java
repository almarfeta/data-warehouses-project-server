package com.example.data_warehouses_project_server.domain.oltp.repository;

import com.example.data_warehouses_project_server.domain.oltp.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

    Optional<PriceEntity> findByIdAndProductId(Long id, Long productId);

    void deleteAllByProductId(Long productId);

    List<PriceEntity> findAllByProductId(Long productId);
}
