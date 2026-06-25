package com.lld.atm;

import java.util.Map;

//4. Withdrawal Transaction (The most complex)
public class WithdrawalTransaction implements Transaction {
 private final ATM atm;
 private final String accountId;
 private final int amount;

 public WithdrawalTransaction(ATM atm, String accountId, int amount) {
     this.atm = atm;
     this.accountId = accountId;
     this.amount = amount;
 }

 @Override
 public void execute() {
     // 1. Validate Bank Balance First
     double balance = atm.getBankService().getBalance(accountId);
     if (balance < amount) {
         System.out.println("Transaction Failed: Insufficient funds in bank account.");
         return;
     }

     // 2. Validate & Commit ATM Inventory 
     try {
         // Our dispenseCash method natively handles the validation-before-commit requirement.
         // It calculates if it has the exact notes needed before deducting anything.
         Map<Integer, Integer> dispensedNotes = atm.getCashDispenser().dispenseCash(amount);
         
         // 3. Commit Bank Debit
         atm.getBankService().debit(accountId, amount);
         
         System.out.println("Please take your cash:");
         dispensedNotes.forEach((denom, count) -> 
             System.out.println(" - $" + denom + " x " + count)
         );
         System.out.println("Transaction successful. Remaining bank balance: $" + 
                            atm.getBankService().getBalance(accountId));

     } catch (CashDispenseException e) {
         // Catches scenarios where the ATM is out of money or can't make exact change
         System.out.println("Transaction Failed: " + e.getMessage());
     }
 }
}