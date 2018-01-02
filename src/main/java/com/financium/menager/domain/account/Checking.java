package com.financium.menager.domain.account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(AccountDiscriminator.CHECKING)
public class Checking extends Account {
    public Checking(Double balance) {
        super(balance);
    }
}
