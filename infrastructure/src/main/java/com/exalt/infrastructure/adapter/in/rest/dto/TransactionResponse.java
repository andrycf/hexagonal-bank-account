package com.exalt.infrastructure.adapter.in.rest.dto;

import java.time.LocalDateTime;

public record TransactionResponse(LocalDateTime timestamp, double amount) {
}
