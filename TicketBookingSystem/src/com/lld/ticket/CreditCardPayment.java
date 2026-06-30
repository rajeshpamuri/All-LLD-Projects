package com.lld.ticket;

public class CreditCardPayment implements PaymentMethod {
	
    private String cardNumber;
    public CreditCardPayment(String cardNumber) { this.cardNumber = cardNumber; }

    @Override
    public boolean processPayment(double amount) {
        // In a real system, this would call an external API like Stripe or Braintree.
        System.out.println("Processing $" + amount + " via Credit Card...");
        return true; // Simulating a successful payment.
    }
    
}
