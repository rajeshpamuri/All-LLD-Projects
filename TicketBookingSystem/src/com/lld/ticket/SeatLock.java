package com.lld.ticket;

import java.time.LocalDateTime;

public class SeatLock {
  
	Seat seat;
    String userId;
    LocalDateTime expiryTime;

    public SeatLock(Seat seat, String userId, int timeoutSeconds) {
        this.seat = seat;
        this.userId = userId;
        // Calculates exactly when this lock should automatically expire.
        this.expiryTime = LocalDateTime.now().plusSeconds(timeoutSeconds);
    }

    // Helper method to check if the lock has timed out.
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryTime);
    }
}
