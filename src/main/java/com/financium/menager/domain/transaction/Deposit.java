package com.financium.menager.domain.transaction;

import com.financium.menager.domain.account.Account;

import javax.persistence.*;

@Entity
@DiscriminatorValue(TransactionDiscriminator.DEPOSITS)
public class Deposit extends Transaction {
    @JoinColumn(name = "TRANSFER_FROM", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Account transferFrom;

    public Account getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(Account transferFrom) {
        this.transferFrom = transferFrom;
    }
}
