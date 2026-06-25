package com.lld.atm;

public class Card {
	
	private final String cardNumber;
    private final String pin; // In a real system, this would be hashed/encrypted
    private final String accountId;

    public Card(String cardNumber, String pin, String accountId) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.accountId = accountId;
    }

    public String getCardNumber() { return cardNumber; }
    public String getPin() { return pin; }
    public String getAccountId() { return accountId; }
}
