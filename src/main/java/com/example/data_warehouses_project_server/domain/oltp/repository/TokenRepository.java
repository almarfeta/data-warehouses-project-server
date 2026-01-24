package com.example.data_warehouses_project_server.domain.oltp.repository;

import com.example.data_warehouses_project_server.domain.oltp.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    @Query(value = """
      select t from token t inner join account u on t.account.id = u.id
      where u.id = :userId and (t.expired = false or t.revoked = false)
    """)
    List<TokenEntity> findAllValidTokenByUser(@Param("userId") Long userId);

    Optional<TokenEntity> findByToken(String tokenEntity);

    void deleteAllByAccountId(Long accountId);
}
