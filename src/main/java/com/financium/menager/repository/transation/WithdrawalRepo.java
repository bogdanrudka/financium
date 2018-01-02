package com.financium.menager.repository.transation;

import com.financium.menager.domain.transaction.Withdrawal;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WithdrawalRepo extends TransactionRepository<Withdrawal>{

    List<Withdrawal> findAllByOwner(Long id, Pageable pageable);
}
