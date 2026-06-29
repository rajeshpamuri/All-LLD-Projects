package com.lld.limiter;

/**
 * Factory class to encapsulate the creation logic of Rate Limiters.
 */
public class RateLimiterFactory {
	
	// Requirements: 100 requests per 60 seconds (60,000 ms)
    private static final int MAX_REQUESTS = 100;
    private static final long TIME_WINDOW_MS = 60000;
    private static final long REFILL_RATE_PER_SEC = (MAX_REQUESTS * 1000L) / TIME_WINDOW_MS; 

    public static RateLimiter createRateLimiter(RateLimitAlgorithm algorithm) {
        switch (algorithm) {
            case FIXED_WINDOW:
                return new FixedWindowRateLimiter(MAX_REQUESTS, TIME_WINDOW_MS);
            case TOKEN_BUCKET:
                return new TokenBucketRateLimiter(MAX_REQUESTS, REFILL_RATE_PER_SEC);
            default:
                throw new IllegalArgumentException("Unknown Algorithm");
        }
    }

}
