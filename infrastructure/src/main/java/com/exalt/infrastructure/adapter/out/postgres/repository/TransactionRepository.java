package com.exalt.infrastructure.adapter.out.postgres.repository;

import com.exalt.infrastructure.adapter.out.postgres.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository  extends JpaRepository<TransactionEntity,Long> {
    List<TransactionEntity> findAllByAccountNumber(String accountNumber);
}
