package com.exalt.infrastructure.adapter.out.postgres.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

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
