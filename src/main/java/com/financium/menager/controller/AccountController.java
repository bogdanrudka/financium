package com.financium.menager.controller;

import com.financium.menager.dto.request.AccountRequest;
import com.financium.menager.dto.request.WithdrawalRequest;
import com.financium.menager.dto.response.AccountDto;
import com.financium.menager.dto.response.WithdrawalResponse;
import com.financium.menager.service.AccountService;
import com.financium.menager.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("v1/accounts")
public class AccountController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;

    @ApiOperation("Get all accounts for specified user")
    @GetMapping(params = "ownerId")
    public ResponseEntity<List<AccountDto>> getAccountsForUser(@PathParam("ownerId") Long ownerId) {
        List<AccountDto> result = accountService.getAccountsByUser(ownerId);
        return result.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
    }

    @ApiOperation("Get all accounts")
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> result = accountService.getAllAccounts();
        return result.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
    }

    @PostMapping("/cash")
    public ResponseEntity<?> addAccount(AccountRequest request) {
        Long result = accountService.createCashAccount(request);
        return created(fromCurrentRequest().path("/{id}").buildAndExpand(result).toUri()).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(Long id) {
        return ofNullable(accountService.getAccount(id)).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/withdrawals")
    public ResponseEntity<?> addWithdraw(@PathVariable Long id, @RequestBody WithdrawalRequest request) {
        Long result = transactionService.withdraw(id, request);
        return created(fromCurrentRequest().path("/{id}").buildAndExpand(result).toUri()).build();
    }

    @GetMapping("/{id}/withdrawals")
    public ResponseEntity<List<WithdrawalResponse>> getAllWithdrawal(@PathVariable Long id, Pageable pageable) {
        List<WithdrawalResponse> result = transactionService.getAllWithdrawals(id, pageable).parallelStream()
                .peek(w -> w.add(linkTo(methodOn(UserController.class).getUser(w.getOwnerId())).withSelfRel()))
                .collect(Collectors.toList());
        return result.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
    }
}
