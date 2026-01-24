package com.example.data_warehouses_project_server.domain.oltp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.data_warehouses_project_server.domain.oltp.entity.OrderItemEntity;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    void deleteAllByOrderId(Long orderId);

    @Query("select oi from orderItem oi  join fetch oi.price join fetch oi.order o join fetch o.delivery")
    List<OrderItemEntity> findAllWithPriceAndOrderAndDelivery();
}
