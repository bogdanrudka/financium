package com.financium.menager.repository.transation;

import com.financium.menager.domain.transaction.Transaction;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface TransactionRepository<T extends Transaction> extends PagingAndSortingRepository<T, Long> {

}
