package com.example.data_warehouses_project_server.domain.oltp.repository;

import com.example.data_warehouses_project_server.domain.oltp.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    Optional<AddressEntity> findByIdAndCustomerId(Long id, Long customerId);

    @Query("select a from address a join fetch a.customer c join fetch c.account")
    List<AddressEntity> findAllWithCustomerAndAccount();

    @Query("select a from address a join fetch a.customer c join fetch c.account ac where ac.id = :accountId")
    List<AddressEntity> findAllByAccountIdWithCustomerAndAccount(@Param("accountId") Long accountId);
}
