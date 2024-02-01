package com.exalt.domain.model;

import java.time.LocalDateTime;

public record Transaction(String accountNumber, LocalDateTime timestamp, double amount) {
}
