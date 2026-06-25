package com.lld.atm;

//3. Deposit Transaction
public class DepositTransaction implements Transaction {
 private final MyBank bankService;
 private final String accountId;
 private final int amount;

 public DepositTransaction(MyBank bankService, String accountId, int amount) {
     this.bankService = bankService;
     this.accountId = accountId;
     this.amount = amount;
 }

 @Override
 public void execute() {
     bankService.credit(accountId, amount);
     System.out.println("$" + amount + " successfully deposited.");
 }
}