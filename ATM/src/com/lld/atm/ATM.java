package com.lld.atm;

public class ATM {
    private final ATMState idleState;
    private final ATMState cardInsertedState;
    private final ATMState authenticatedState;

    private ATMState currentState; // "Who is currently at the counter?"

    private final MyBank bankService;
    private final CashDispenser cashDispenser;
    private Card currentCard;

    public ATM(MyBank bankService, CashDispenser cashDispenser) {
        this.bankService = bankService;
        this.cashDispenser = cashDispenser;
        
        // Initialize the states, passing 'this' so they can trigger transitions
        this.idleState = new IdleState(this);
        this.cardInsertedState = new CardInsertedState(this);
        this.authenticatedState = new AuthenticatedState(this);
        
        this.currentState = idleState; // ATM starts out Idle
    }

    // --- State Management ---
    public void setState(ATMState state) { this.currentState = state; }
    public ATMState getIdleState() { return idleState; }
    public ATMState getCardInsertedState() { return cardInsertedState; }
    public ATMState getAuthenticatedState() { return authenticatedState; }
    
    // --- Getters for Dependencies ---
    public MyBank getBankService() { return bankService; }
    public CashDispenser getCashDispenser() { return cashDispenser; }
    public void setCurrentCard(Card card) { this.currentCard = card; }
    public Card getCurrentCard() { return currentCard; }

    // --- The Delegation Methods (No if/else needed!) ---
    public void insertCard(Card card) { currentState.insertCard(card); }
    public void authenticate(String pin) { currentState.authenticate(pin); }
    public void withdraw(int amount) { currentState.withdraw(amount); }
    public void deposit(int amount) { currentState.deposit(amount); }
    public void checkBalance() { currentState.checkBalance(); }
    public void ejectCard() { currentState.ejectCard(); }
}
