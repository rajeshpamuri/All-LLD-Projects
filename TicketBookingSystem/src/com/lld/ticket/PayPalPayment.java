package com.lld.ticket;

//Concrete implementation for PayPal payments.
public class PayPalPayment implements PaymentMethod {
	
 private String email;
 public PayPalPayment(String email) { this.email = email; }

 @Override
 public boolean processPayment(double amount) {
     System.out.println("Processing $" + amount + " via PayPal...");
     return true; // Simulating a successful payment.
 }
 
}
