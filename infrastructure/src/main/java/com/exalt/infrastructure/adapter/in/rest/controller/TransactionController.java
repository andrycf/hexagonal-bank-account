package com.exalt.infrastructure.adapter.in.rest.controller;


import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionRequest;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionResponse;
import com.exalt.infrastructure.adapter.in.rest.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        path = "/transaction",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Bank Transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(
            summary = "Consulter les transactions précédentes",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/{accountNumber}")
    public List<TransactionResponse> getTransactionHistory(@PathVariable String accountNumber){
        return transactionService.checkTransactionHistory(accountNumber);
    }

    @Operation(
            summary = "Dépot d'argent",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @PatchMapping(value = "/deposit",consumes = MediaType.APPLICATION_JSON_VALUE)
    public AccountDTO deposit(@RequestBody TransactionRequest transactionRequest){
        return transactionService.depositAmount(transactionRequest);
    }

    @Operation(
            summary = "Retrait d'argent",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @PatchMapping(value = "/withdraw",consumes = MediaType.APPLICATION_JSON_VALUE)
    public AccountDTO withdraw(@RequestBody TransactionRequest transactionRequest){
        return transactionService.withdrawAmount(transactionRequest);
    }
}
