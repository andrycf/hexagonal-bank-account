package com.exalt.infrastructure.adapter.out.postgres.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalt.infrastructure.adapter.out.postgres.entity.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {
    Optional<AccountEntity> findByAccountNumber(String accountNumber);
}
