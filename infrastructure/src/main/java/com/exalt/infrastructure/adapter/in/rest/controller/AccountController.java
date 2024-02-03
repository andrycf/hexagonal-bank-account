package com.exalt.infrastructure.adapter.in.rest.controller;

import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;
import com.exalt.infrastructure.adapter.in.rest.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = "/account",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Bank Account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(@RequestBody AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(
            summary = "Consulter le solde actuel",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/{accountNumber}")
    public Mono<AccountDTO> getBalance(@PathVariable String accountNumber){
        return Mono.just(accountService.checkBalance(accountNumber));
    }

    @Operation(
            summary = "Creation compte",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AccountDTO> create(@RequestBody AccountDTO accountDTO){
        return Mono.just(accountService.createAccount(accountDTO));
    }
}
