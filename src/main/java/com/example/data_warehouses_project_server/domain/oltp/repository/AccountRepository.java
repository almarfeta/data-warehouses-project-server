package com.example.data_warehouses_project_server.domain.oltp.repository;

import com.example.data_warehouses_project_server.domain.oltp.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByUsername(String username);
    Optional<AccountEntity> findByEmail(String email);
}
