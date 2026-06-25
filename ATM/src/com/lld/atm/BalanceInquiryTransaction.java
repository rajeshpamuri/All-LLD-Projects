package com.lld.atm;

//2. Balance Inquiry Transaction
public class BalanceInquiryTransaction implements Transaction {
 private final MyBank bankService;
 private final String accountId;

 public BalanceInquiryTransaction(MyBank bankService, String accountId) {
     this.bankService = bankService;
     this.accountId = accountId;
 }

 @Override
 public void execute() {
     double balance = bankService.getBalance(accountId);
     System.out.println("Current Balance: $" + balance);
 }
}