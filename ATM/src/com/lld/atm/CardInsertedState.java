package com.lld.atm;

//4. Concrete State: CARD INSERTED
public class CardInsertedState implements ATMState {
 private final ATM atm;

 public CardInsertedState(ATM atm) { this.atm = atm; }

 @Override
 public void insertCard(Card card) { System.out.println("Card already inserted."); }

 @Override
 public void authenticate(String pin) {
     boolean isValid = atm.getBankService().authenticateUser(atm.getCurrentCard(), pin);
     if (isValid) {
         System.out.println("PIN verified.");
         atm.setState(atm.getAuthenticatedState());
     } else {
         System.out.println("Invalid PIN.");
         ejectCard(); // Automatically eject on failure
     }
 }

 @Override
 public void withdraw(int amount) { System.out.println("Please enter your PIN first."); }
 
 @Override
 public void deposit(int amount) { System.out.println("Please enter your PIN first."); }
 
 @Override
 public void checkBalance() { System.out.println("Please enter your PIN first."); }

 @Override
 public void ejectCard() {
     System.out.println("Card ejected.");
     atm.setCurrentCard(null);
     atm.setState(atm.getIdleState());
 }
}
