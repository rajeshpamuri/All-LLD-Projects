package com.lld.atm;

public interface MyBank {
	boolean authenticateUser(Card card, String enteredPin);
    double getBalance(String accountId);
    boolean debit(String accountId, double amount);
    void credit(String accountId, double amount);
}
