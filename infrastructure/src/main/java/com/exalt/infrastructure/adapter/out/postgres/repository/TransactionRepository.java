package com.exalt.infrastructure.adapter.out.postgres.repository;

import com.exalt.infrastructure.adapter.out.postgres.entity.TransactionEntity;


import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository  extends JpaRepository<TransactionEntity,Long> {
    List<TransactionEntity> findAllByAccountNumber(String accountNumber);
    Window<TransactionEntity> findByAccountNumber(String accountNumber,ScrollPosition position, Limit limit, Sort sort);
}
