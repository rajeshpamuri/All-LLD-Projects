package com.lld.atm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyBankImp implements MyBank{
     
	// Thread-safe map to store account balances (Simulating a database)
    // Key: AccountID, Value: Balance
    private final Map<String, Double> accountBalances = new ConcurrentHashMap<>();

    public void addAccount(String accountId, double initialBalance) {
        accountBalances.put(accountId, initialBalance);
    }

    @Override
    public boolean authenticateUser(Card card, String enteredPin) {
        // Simple string matching for demo purposes
        return card != null && card.getPin().equals(enteredPin);
    }

    @Override
    public double getBalance(String accountId) {
        return accountBalances.getOrDefault(accountId, 0.0);
    }

    @Override
    public synchronized boolean debit(String accountId, double amount) {
        double currentBalance = getBalance(accountId);
        if (currentBalance >= amount) {
            accountBalances.put(accountId, currentBalance - amount);
            return true; // Successfully debited
        }
        return false; // Insufficient funds
    }

    @Override
    public synchronized void credit(String accountId, double amount) {
        double currentBalance = getBalance(accountId);
        accountBalances.put(accountId, currentBalance + amount);
    }
}
