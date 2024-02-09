package com.exalt.infrastructure.adapter.out.postgres.mapper;

import com.exalt.domain.model.Transaction;
import com.exalt.infrastructure.adapter.out.postgres.entity.TransactionEntity;

public class TransactionEntityMapperAdapter {
    public static Transaction toMetier(TransactionEntity entity) {
        return new Transaction(
                entity.getAccountNumber(),
                entity.getTimestamp(),
                entity.getAmount());
    }

    public static TransactionEntity toEntity(Transaction transaction) {
        return new TransactionEntity(
                transaction.accountNumber(),
                transaction.timestamp(),
                transaction.amount());
    }
}
