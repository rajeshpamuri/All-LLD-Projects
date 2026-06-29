package com.lld.limiter;

/**
 * Token Bucket Implementation.
 * Thread-safe using synchronized block to prevent race conditions during token refill and consumption.
 */

public class TokenBucketRateLimiter implements RateLimiter {
	
	private final long maxBucketSize;
    private final double refillRatePerMilli; // Tokens added per millisecond
    
    private double currentTokens;
    private long lastRefillTimestamp;

    public TokenBucketRateLimiter(long maxBucketSize, long refillRatePerSecond) {
        this.maxBucketSize = maxBucketSize;
        this.refillRatePerMilli = refillRatePerSecond / 1000.0;
        this.currentTokens = maxBucketSize; // Start with a full bucket
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean tryAcquire() {
        refillTokens();

        if (currentTokens >= 1.0) {
            currentTokens -= 1.0;
            return true;
        }
        
        return false;
    }

    private void refillTokens() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastRefillTimestamp;
        
        double tokensToAdd = elapsedTime * refillRatePerMilli;
        
        if (tokensToAdd > 0) {
            currentTokens = Math.min(maxBucketSize, currentTokens + tokensToAdd);
            lastRefillTimestamp = currentTime;
        }
    }
}

