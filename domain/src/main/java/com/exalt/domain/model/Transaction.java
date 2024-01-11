package com.exalt.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record Transaction(String accountNumber, LocalDateTime timestamp, double amount) {
}
