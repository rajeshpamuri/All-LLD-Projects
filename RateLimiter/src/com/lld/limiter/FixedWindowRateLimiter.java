package com.lld.limiter;
//==========================================
//3. Algorithm Implementations
//==========================================

/**
* Fixed Window Counter Implementation.
* Thread-safe using synchronized block to prevent race conditions during window reset.
*/

public class FixedWindowRateLimiter implements RateLimiter {
	
	private final int maxRequests;
    private final long windowSizeInMillis;
    
    private int counter;
    private long windowStartTime;

    public FixedWindowRateLimiter(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.windowStartTime = System.currentTimeMillis();
        this.counter = 0;
    }
	
    @Override
    public synchronized boolean tryAcquire() {
        long currentTime = System.currentTimeMillis();
        
        // If the current time has passed the window, reset the window and counter
        if (currentTime - windowStartTime >= windowSizeInMillis) {
            windowStartTime = currentTime;
            counter = 0;
        }

        if (counter < maxRequests) {
            counter++;
            return true;
        }
        
        return false;
    }

}
