package com.lld.ticket;

import java.util.List;

public class TicketBookingFacade {
	
    private SearchService searchService;
    private SeatLockManager lockManager;
    private PricingStrategy pricingStrategy;
    
    // Configurable timeout for how long a user has to complete payment.
    private static final int LOCK_TIMEOUT_SECONDS = 300; // 5 minutes

    // The dependencies are injected via the constructor (Dependency Injection).
    public TicketBookingFacade(SearchService searchService, SeatLockManager lockManager, PricingStrategy pricingStrategy) {
        this.searchService = searchService;
        this.lockManager = lockManager;
        this.pricingStrategy = pricingStrategy;
    }

    // Delegates the search query to the SearchService.
    public List<Show> searchShows(Movie movie, City city) {
        return searchService.getShowsByMovieAndCity(movie, city);
    }

    // Step 1 of the booking workflow: User selects seats and clicks "Checkout".
    public Booking initiateBooking(String userId, Show show, List<Seat> seats) {
        // Attempt to lock the seats in the LockManager.
        boolean locked = lockManager.lockSeats(show, seats, userId, LOCK_TIMEOUT_SECONDS);
        if (!locked) {
            System.out.println("Failed to lock seats. They may be unavailable.");
            return null; // Return null to indicate to the UI that the seats are gone.
        }

        // Delegate price calculation to the injected PricingStrategy.
        double amount = pricingStrategy.calculatePrice(show, seats);

        // Create and return the pending booking record.
        Booking booking = new Booking(userId, show, seats, amount);
        System.out.println("Booking initiated. Seats locked for 5 minutes. Total: $" + amount);
        return booking;
    }

    // Step 2 of the booking workflow: User submits payment details.
    public BookingStatus confirmBooking(Booking booking, PaymentMethod paymentMethod) {
        // Prevent double processing if already confirmed or failed.
        if (booking.status != BookingStatus.PENDING) {
            return booking.status;
        }

        // Process payment via the provided PaymentMethod strategy.
        boolean paymentSuccess = paymentMethod.processPayment(booking.amount);
        if (!paymentSuccess) {
            booking.status = BookingStatus.FAILED;
            System.out.println("Payment failed.");
            return booking.status;
        }

        // If payment succeeds, tell the LockManager to convert temporary locks to permanent bookings.
        boolean confirmed = lockManager.confirmBooking(booking.show, booking.seats, booking.userId);
        
        if (confirmed) {
            booking.status = BookingStatus.CONFIRMED;
            System.out.println("Booking confirmed! Booking ID: " + booking.bookingId);
        } else {
            // Edge Case: The payment took longer than 5 minutes, and the lock expired before we got here.
            // In a real system, we would trigger an asynchronous refund process here.
            booking.status = BookingStatus.EXPIRED;
            System.out.println("Booking failed. Seat locks expired before payment confirmation. Initiating refund.");
        }

        return booking.status;
    }
    
    }
