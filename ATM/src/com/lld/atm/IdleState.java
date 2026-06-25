package com.lld.atm;

//3. Concrete State: IDLE
public class IdleState implements ATMState {
 private final ATM atm;

 public IdleState(ATM atm) { this.atm = atm; }

 @Override
 public void insertCard(Card card) {
     System.out.println("Card inserted.");
     atm.setCurrentCard(card);
     atm.setState(atm.getCardInsertedState());
 }

 @Override
 public void authenticate(String pin) { System.out.println("Please insert a card first."); }
 
 @Override
 public void withdraw(int amount) { System.out.println("Please insert a card first."); }
 
 @Override
 public void deposit(int amount) { System.out.println("Please insert a card first."); }
 
 @Override
 public void checkBalance() { System.out.println("Please insert a card first."); }
 
 @Override
 public void ejectCard() { System.out.println("No card to eject."); }
}
