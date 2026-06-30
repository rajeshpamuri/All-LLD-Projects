package com.lld.ticket;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SeatLockManager {

private final Map<String, SeatLock> locks = new ConcurrentHashMap<>();
    
    // ConcurrentKeySet tracks seats that have been successfully paid for and permanently booked.
    private final Set<String> bookedSeats = ConcurrentHashMap.newKeySet();

    // Generates a unique identifier for a seat within a specific show to avoid conflicts across different shows.
    private String getSeatKey(Show show, Seat seat) {
        return show.id + "_" + seat.id;
    }

    // 'synchronized' ensures only one thread can execute this block at a time for this instance.
    // This prevents two users from checking availability and locking the same seat at the exact same millisecond.
    public synchronized boolean lockSeats(Show show, List<Seat> seats, String userId, int timeoutSeconds) {
        // Step 1: Verify all requested seats are available before locking any of them.
        for (Seat seat : seats) {
            String key = getSeatKey(show, seat);
            
            // If the seat is in the permanently booked set, it's unavailable.
            if (bookedSeats.contains(key)) return false; 
            
            // If the seat is in the temporary locks map, check who owns the lock and if it's expired.
            SeatLock existingLock = locks.get(key);
            if (existingLock != null && !existingLock.isExpired() && !existingLock.userId.equals(userId)) {
                return false; // Locked actively by a different user.
            }
        }

        // Step 2: If we reach here, ALL requested seats are available. Lock them atomically.
        for (Seat seat : seats) {
            String key = getSeatKey(show, seat);
            locks.put(key, new SeatLock(seat, userId, timeoutSeconds));
        }
        return true; // Successfully locked all requested seats.
    }

    // Called after a successful payment to convert temporary locks into permanent bookings.
    public synchronized boolean confirmBooking(Show show, List<Seat> seats, String userId) {
        // Step 1: Verify the user still holds valid, unexpired locks for all seats.
        for (Seat seat : seats) {
            String key = getSeatKey(show, seat);
            SeatLock lock = locks.get(key);
            
            // If the lock doesn't exist, belongs to someone else, or expired while they were paying, fail the confirmation.
            if (lock == null || !lock.userId.equals(userId) || lock.isExpired()) {
                return false; 
            }
        }

        // Step 2: Move the seats from the temporary 'locks' map to the permanent 'bookedSeats' set.
        for (Seat seat : seats) {
            String key = getSeatKey(show, seat);
            bookedSeats.add(key);
            locks.remove(key); // Free up the locks map.
        }
        return true;
    }
}
