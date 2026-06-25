package com.lld.atm;

public class ATMSystemDemo {

	public static void main(String[] args) {
        // 1. Initialize the Bank Backend
        MyBankImp bankService = new MyBankImp();
        // Create a test account with a $500 balance
        bankService.addAccount("ACC-12345", 500.0);
        
        // 2. Initialize the ATM Hardware Components
        CashDispenser dispenser = new CashDispenser();
        // Load the ATM with some cash: 10x$100, 10x$50, 10x$20, 10x$10
        dispenser.loadCash(100, 10); 
        dispenser.loadCash(50, 10);
        dispenser.loadCash(20, 10);
        dispenser.loadCash(10, 10);
        
        // 3. Boot up the ATM
        ATM atm = new ATM(bankService, dispenser);
        
        // 4. Create a Customer Card
        Card myCard = new Card("9876-5432-1098-7654", "1234", "ACC-12345");
        
        System.out.println("--- SCENARIO 1: Successful Withdrawal ---");
        atm.insertCard(myCard);              // Transitions to CardInsertedState
        atm.authenticate("1234");            // Transitions to AuthenticatedState
        atm.checkBalance();                  // Prints $500.0
        atm.withdraw(170);                   // Dispenses $100x1, $50x1, $20x1
        atm.ejectCard();                     // Transitions back to IdleState
        
        System.out.println("\n--- SCENARIO 2: Edge Case (Wrong PIN) ---");
        atm.insertCard(myCard);
        atm.authenticate("9999");            // Fails, automatically ejects card
        atm.withdraw(50);                    // Fails safely because state is Idle
        
        System.out.println("\n--- SCENARIO 3: Edge Case (ATM Out of exact change) ---");
        atm.insertCard(myCard);
        atm.authenticate("1234");
        // User has $330 left, but ATM might not have exact bills if we drain it
        atm.withdraw(330); 
        atm.ejectCard();
    }
}
