package com.financium.menager.service;

import com.financium.menager.domain.account.Account;
import com.financium.menager.domain.transaction.Withdrawal;
import com.financium.menager.dto.request.WithdrawalRequest;
import com.financium.menager.dto.response.WithdrawalResponse;
import com.financium.menager.repository.AccountRepository;
import com.financium.menager.repository.CategoryRepository;
import com.financium.menager.repository.TagRepository;
import com.financium.menager.repository.UserRepository;
import com.financium.menager.repository.transation.WithdrawalRepo;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.StreamSupport.stream;

@Service
public class TransactionService {

    private final WithdrawalRepo withdrawalRepo;
    private final UserRepository userRepo;
    private final AccountRepository accountRepo;
    private final TagRepository tagRepo;
    private final CategoryRepository categoryRepo;

    @Autowired
    public TransactionService(WithdrawalRepo transactionRepo, UserRepository userRepo, AccountRepository accountRepo, TagRepository tagRepo, CategoryRepository categoryRepo) {
        this.withdrawalRepo = transactionRepo;
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
        this.tagRepo = tagRepo;
        this.categoryRepo = categoryRepo;
    }

    @Transactional
    public Long withdraw(Long id, WithdrawalRequest request) {
        Account account = accountRepo.findOne(id);
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setOwner(userRepo.findOne(request.getOwnerId()));
        withdrawal.setValue(request.getValue());
        withdrawal.setTransferFrom(account);
        ofNullable(request.getTagsId()).map(tagRepo::findAll).map(Sets::newHashSet).ifPresent(withdrawal::setTags);
        ofNullable(request.getCategoryId()).map(categoryRepo::findOne).ifPresent(withdrawal::setCategory);

        withdrawalRepo.save(withdrawal);
        account.setBalance(account.getBalance() - request.getValue());
        accountRepo.save(account);
        return withdrawal.getId();
    }

    public Withdrawal getWithdraw(Long id) {
        return (Withdrawal) withdrawalRepo.findOne(id);
    }

    public List<WithdrawalResponse> getAllWithdrawals(Long id, Pageable pageable){
        return stream(withdrawalRepo.findAllByOwner(id, pageable).spliterator(), false)
                .map(this::mapWithdrawal)
                .collect(Collectors.toList());
    }

    public List<WithdrawalResponse> getAllWithdrawals(Pageable pageable) {
        return stream(withdrawalRepo.findAll(pageable).spliterator(), false)
                .map(this::mapWithdrawal)
                .collect(Collectors.toList());
    }

    private WithdrawalResponse mapWithdrawal(Withdrawal w) {
        WithdrawalResponse r = new WithdrawalResponse();
        r.setDescription(w.getDescription());
        r.setDateCreated(w.getDateCreated().toString());
        r.setAccountName(w.getTransferFrom().getName());
        r.setAccountId(w.getTransferFrom().getId());
        return r;
    }
}
