package com.financium.menager.service;

import com.financium.menager.domain.account.Account;
import com.financium.menager.domain.account.Cash;
import com.financium.menager.dto.request.AccountRequest;
import com.financium.menager.dto.response.AccountDto;
import com.financium.menager.repository.AccountRepository;
import com.financium.menager.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.StreamSupport.stream;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    private static AccountDto mapAccount(Account account) {
        AccountDto dto = new AccountDto();
        dto.setBalance(account.getBalance());
        dto.setName(account.getName());
        return dto;
    }

    public AccountDto getAccount(Long id) {
        return ofNullable(accountRepository.findOne(id)).map(account -> {
            AccountDto result = new AccountDto();
            result.setBalance(account.getBalance());
            result.setName(account.getName());
            return result;
        }).orElse(null);
    }

    public Long createCashAccount(AccountRequest request) {
        Account account = new Cash();
        account.setBalance(request.getStartBalance());
        account.setDescription(request.getDescription());
        account.setName(request.getName());
        accountRepository.save(account);
        return account.getId();
    }

    public List<AccountDto> getAccountsByUser(Long userId) {
        return userRepository.findOne(userId).getAccounts().stream().map(AccountService::mapAccount)
                .collect(Collectors.toList());
    }

    public List<AccountDto> getAllAccounts() {
        return stream(accountRepository.findAll().spliterator(), false).map(AccountService::mapAccount)
                .collect(Collectors.toList());
    }
}
