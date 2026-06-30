package com.lld.ticket;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {
  
	public static void main(String[] args) {
        System.out.println("=== INITIALIZING CINEMA SYSTEM ===\n");
        
        // 1. Setup Master Data
        City cityNY = new City("C1", "New York");
        Movie inception = new Movie("M1", "Inception");
        
        // Create Seats
        Seat s1 = new Seat("S1", 1, 1, SeatType.REGULAR);
        Seat s2 = new Seat("S2", 1, 2, SeatType.PREMIUM);
        Seat s3 = new Seat("S3", 1, 3, SeatType.RECLINER);
        
        Screen screen1 = new Screen("SC1", "IMAX Screen", Arrays.asList(s1, s2, s3));
        Cinema amc = new Cinema("CIN1", "AMC Empire", cityNY, Arrays.asList(screen1));
        
        // Create a Show for tomorrow
        Show show1 = new Show("SH1", inception, amc, screen1, LocalDateTime.now().plusDays(1));

        // 2. Initialize Services and Facade
        SearchService searchService = new SearchService();
        searchService.addShow(show1);
        
        SeatLockManager lockManager = new SeatLockManager();
          PricingStrategy pricingStrategy = new BasePricingStrategy();
        
        TicketBookingFacade bookingFacade = new TicketBookingFacade(searchService, lockManager, pricingStrategy);

        // ==========================================
        // SCENARIO 1: Happy Path (Successful Booking)
        // ==========================================
        System.out.println("--- SCENARIO 1: Happy Path ---");
        List<Show> searchResults = bookingFacade.searchShows(inception, cityNY);
        Show selectedShow = searchResults.get(0);
        
        // User 1 tries to book Seat 1 (Regular) and Seat 2 (Premium)
        List<Seat> user1Seats = Arrays.asList(s1, s2);
        Booking user1Booking = bookingFacade.initiateBooking("USER_123", selectedShow, user1Seats);
        
        if (user1Booking != null) {
            PaymentMethod ccPayment = new CreditCardPayment("1111-2222-3333-4444");
            BookingStatus finalStatus = bookingFacade.confirmBooking(user1Booking, ccPayment);
            System.out.println("Final Status for User 1: " + finalStatus + "\n");
        }

        // ==========================================
        // SCENARIO 2: Concurrency / Double Booking 
        // ==========================================
        System.out.println("--- SCENARIO 2: Double Booking Attempt ---");
        // User 2 tries to book Seat 1, which is now permanently booked by User 1
        List<Seat> user2Seats = Arrays.asList(s1);
        Booking user2Booking = bookingFacade.initiateBooking("USER_999", selectedShow, user2Seats);
        
        if (user2Booking == null) {
            System.out.println("System successfully prevented User 2 from booking an already booked seat.\n");
        }

        // ==========================================
        // SCENARIO 3: Temporary Lock Collision
        // ==========================================
        System.out.println("--- SCENARIO 3: Temporary Lock Collision ---");
        // User 3 initiates booking for Seat 3 (Recliner) but hasn't paid yet.
        List<Seat> user3Seats = Arrays.asList(s3);
        Booking user3Booking = bookingFacade.initiateBooking("USER_333", selectedShow, user3Seats);
        
        // User 4 tries to book the exact same Seat 3 while User 3 is still on the payment screen.
        System.out.println("User 4 attempting to book Seat 3 while User 3 holds the lock...");
        Booking user4Booking = bookingFacade.initiateBooking("USER_444", selectedShow, user3Seats);
        
        if (user4Booking == null) {
            System.out.println("System successfully prevented User 4 from interrupting User 3's checkout.\n");
        }

        // ==========================================
        // SCENARIO 4: Payment Failure
        // ==========================================
        System.out.println("--- SCENARIO 4: Payment Failure ---");
        // User 3 now tries to pay, but we use a payment method that fails
        if (user3Booking != null) {
            PaymentMethod failingPayment = new PaymentMethod() {
                @Override
                public boolean processPayment(double amount) {
                    System.out.println("Processing $" + amount + " via Bank Transfer...");
                    System.out.println("INSUFFICIENT FUNDS.");
                    return false; // Simulate failure
                }
            };
            
            BookingStatus status = bookingFacade.confirmBooking(user3Booking, failingPayment);
            System.out.println("Final Status for User 3: " + status);
        }
    }
	
}
