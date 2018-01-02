package com.financium.menager.domain.account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
@DiscriminatorValue(AccountDiscriminator.CASH)
public class Cash extends Account{

    public Cash() {
    }

    public Cash(Double balance) {
        super(balance);
    }
}
