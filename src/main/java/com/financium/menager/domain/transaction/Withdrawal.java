package com.financium.menager.domain.transaction;

import com.financium.menager.domain.account.Account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(TransactionDiscriminator.WITHDROWING)
public class Withdrawal extends Transaction{
    @JoinColumn(name = "TRANSFER_FROM", updatable = false)
    @ManyToOne
    private Account transferFrom;

    public Account getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(Account transferFrom) {
        this.transferFrom = transferFrom;
    }
}
