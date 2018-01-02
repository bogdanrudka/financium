package com.financium.menager.controller;

import com.financium.menager.domain.transaction.Withdrawal;
import com.financium.menager.dto.response.WithdrawalResponse;
import com.financium.menager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/withdrawals/{id}")
    public ResponseEntity<Withdrawal> getWithdraw(@PathVariable Long id) {
        return ofNullable(transactionService.getWithdraw(id)).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/withdrawals")
    public ResponseEntity<List<WithdrawalResponse>> getAll(Pageable pageable) {
        List<WithdrawalResponse> allTransactions = transactionService.getAllWithdrawals(pageable);
        return allTransactions.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(allTransactions);
    }
}
