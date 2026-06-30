package com.lld.ticket;

import java.util.List;

//A concrete implementation of the pricing strategy based strictly on seat type.
public class BasePricingStrategy implements PricingStrategy {
   
	public double calculatePrice(Show show, List<Seat> seats) {
        double total = 0;
        for (Seat seat : seats) {
            switch (seat.seatType) {
                case REGULAR: total += 10.0; break;
                case PREMIUM: total += 15.0; break;
                case RECLINER: total += 20.0; break;
            }
        }
        return total;
    }
}
