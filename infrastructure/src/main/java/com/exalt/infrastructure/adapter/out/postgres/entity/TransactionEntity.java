package com.exalt.infrastructure.adapter.out.postgres.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
