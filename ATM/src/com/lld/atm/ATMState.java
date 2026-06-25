package com.lld.atm;

public interface ATMState {

	void insertCard(Card card);
    void authenticate(String pin);
    void withdraw(int amount);
    void deposit(int amount);
    void checkBalance();
    void ejectCard();
}
