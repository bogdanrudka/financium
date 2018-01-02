package com.financium.menager.domain.account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(AccountDiscriminator.CREDIT)
public class Credit extends Account {
    public Credit(Double balance) {
        super(balance);
    }
}
