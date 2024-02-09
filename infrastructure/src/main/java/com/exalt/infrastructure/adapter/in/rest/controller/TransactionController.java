package com.exalt.infrastructure.adapter.in.rest.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionRequest;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionResponse;
import com.exalt.infrastructure.adapter.in.rest.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Bank Transaction")
public class TransactionController {
        private final TransactionService transactionService;

        public TransactionController(TransactionService transactionService) {
                this.transactionService = transactionService;
        }

        @Operation(summary = "Consulter les transactions précédentes", responses = {
                        @ApiResponse(description = "Success", responseCode = "200")
        })
        @GetMapping("/{accountNumber}")
        public Flux<TransactionResponse> getTransactionHistory(@PathVariable String accountNumber) {
                return Flux.fromIterable(transactionService.checkTransactionHistory(accountNumber));
        }

        @Operation(summary = "Dépot d'argent", responses = {
                        @ApiResponse(description = "Success", responseCode = "200")
        })
        @PatchMapping(value = "/deposit", consumes = MediaType.APPLICATION_JSON_VALUE)
        public Mono<AccountDTO> deposit(@RequestBody TransactionRequest transactionRequest) {
                return Mono.just(transactionService.depositAmount(transactionRequest));
        }

        @Operation(summary = "Retrait d'argent", responses = {
                        @ApiResponse(description = "Success", responseCode = "200")
        })
        @PatchMapping(value = "/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE)
        public Mono<AccountDTO> withdraw(@RequestBody TransactionRequest transactionRequest) {
                return Mono.just(transactionService.withdrawAmount(transactionRequest));
        }
}
