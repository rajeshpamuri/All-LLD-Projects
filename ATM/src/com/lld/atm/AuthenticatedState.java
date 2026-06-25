package com.lld.atm;

//---------------------------------------------------------
//5. STATE 3: AUTHENTICATED ("Ready for Transactions")
//---------------------------------------------------------
public class AuthenticatedState implements ATMState {
private final ATM atm;
    
    public AuthenticatedState(ATM atm) { 
        this.atm = atm; 
    }

    @Override 
    public void insertCard(Card card) { 
        System.out.println("Error: Card already inserted."); 
    }
    
    @Override 
    public void authenticate(String pin) { 
        System.out.println("Error: Already authenticated."); 
    }

    @Override
    public void withdraw(int amount) {
        Transaction tx = new WithdrawalTransaction(atm, atm.getCurrentCard().getAccountId(), amount);
        tx.execute();
    }
    
    @Override
    public void deposit(int amount) {
        Transaction tx = new DepositTransaction(atm.getBankService(), atm.getCurrentCard().getAccountId(), amount);
        tx.execute();
    }
    
    @Override
    public void checkBalance() {
        Transaction tx = new BalanceInquiryTransaction(atm.getBankService(), atm.getCurrentCard().getAccountId());
        tx.execute();
    }

    @Override
    public void ejectCard() {
        System.out.println("Transaction complete. Ejecting card...");
        atm.setCurrentCard(null);
        atm.setState(atm.getIdleState()); 
    }
}
