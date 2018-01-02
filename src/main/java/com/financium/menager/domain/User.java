package com.financium.menager.domain;

import com.financium.menager.domain.account.Account;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User extends BaseDomain {
    @Column(name = "LOGIN", nullable = false, updatable = false, unique = true)
    private String login;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @ManyToMany
    @JoinTable(name = "USER_TO_ACCOUNT",
            joinColumns = @JoinColumn(table = "user", name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(table = "account", name = "ACCOUNT_ID", referencedColumnName = "ID")
    )
    private Set<Account> accounts;
    //TODO add user groups

    @Column(name="IS_ACTIVE")
    private Boolean active;

    public User() {
    }

    public User(String login, String firstName, String lastName) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = true;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
