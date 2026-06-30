package com.lld.ticket;

import java.util.List;

//--- Pricing Strategies ---
//Interface defining the contract for calculating prices.
interface PricingStrategy {
  
	double calculatePrice(Show show, List<Seat> seats);
}
