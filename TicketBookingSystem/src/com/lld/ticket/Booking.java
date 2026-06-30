package com.lld.ticket;

import java.util.List;
import java.util.UUID;

//Represents the overall booking record, tying the user, show, seats, and monetary amount together.
class Booking {
	
 String bookingId;
 String userId;
 Show show;
 List<Seat> seats;
 double amount;
 BookingStatus status;

 public Booking(String userId, Show show, List<Seat> seats, double amount) {
     this.bookingId = UUID.randomUUID().toString(); // Generate a unique receipt/booking ID.
     this.userId = userId;
     this.show = show;
     this.seats = seats;
     this.amount = amount;
     this.status = BookingStatus.PENDING; // Initial status before payment.
 }
 
}
