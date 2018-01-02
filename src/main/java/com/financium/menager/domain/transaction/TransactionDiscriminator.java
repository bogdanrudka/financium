package com.financium.menager.domain.transaction;

public final class TransactionDiscriminator {

    public static final String WITHDROWING = "WITHDRAWING";//The funds that was sent to external account(market, shop)
    public static final String DEPOSITS = "DEPOSITS";//funds that some external resource sent to us (salary income)
    public static final String INTERNAL = "INTERNAL";//transfer between registered account

    private TransactionDiscriminator() {
    }
}
