package com.exalt.infrastructure.adapter.out.postgres.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    public TransactionEntity(String accountNumber, LocalDateTime timestamp, double amount) {
        this.accountNumber = accountNumber;
        this.timestamp = timestamp;
        this.amount = amount;
    }

    private String accountNumber;
    private LocalDateTime timestamp;
    private double amount;

}
