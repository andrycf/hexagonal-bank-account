package com.exalt.infrastructure.adapter.out.postgres.entity;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String accountNumber;
    private double balance;
}
