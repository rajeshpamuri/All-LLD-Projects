package com.lld.ticket;

//--- Payment Strategies ---
//Interface defining the contract for processing payments.
interface PaymentMethod {
	
 boolean processPayment(double amount);
 
}
