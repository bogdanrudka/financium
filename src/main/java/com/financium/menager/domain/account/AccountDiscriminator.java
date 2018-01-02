package com.financium.menager.domain.account;

public final class AccountDiscriminator {
    public static final String CASH = "CASH"; //Cash in possession
    public static final String CHECKING = "CHECKING"; //Basic account created for dayle use
    public static final String CREDIT = "CREDIT"; //Credit account that should be refilled
    public static final String SAVING = "SAVING"; //Saving for future
    public static final String IRA = "IRA"; //Individual Retirement Accounts
    public static final String CD = "CD"; //Certificates of Checking, committed to keep money specific amount of time

    private AccountDiscriminator(){

    }
}
