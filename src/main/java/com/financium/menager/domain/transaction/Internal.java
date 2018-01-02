package com.financium.menager.domain.transaction;

import com.financium.menager.domain.account.Account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(TransactionDiscriminator.INTERNAL)
public class Internal extends Transaction{
    @JoinColumn(name = "TRANSFER_FROM", updatable = false)
    @ManyToOne
    private Account transferFrom;
    @JoinColumn(name = "TRANSFER_TO", updatable = false)
    @ManyToOne
    private Account transferTo;

    public Account getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(Account transferFrom) {
        this.transferFrom = transferFrom;
    }

    public Account getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(Account transferTo) {
        this.transferTo = transferTo;
    }
}
