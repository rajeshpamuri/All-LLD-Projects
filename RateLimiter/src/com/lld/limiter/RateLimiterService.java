package com.lld.limiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//==========================================
//5. Manager / Service Layer
//==========================================

/**
* The main service that routes requests per user.
* Acts as the entry point (e.g., used by a Spring Interceptor).
*/

public class RateLimiterService {
	
	// Thread-safe map to hold the rate limiter state for each unique user
    private final Map<String, RateLimiter> userRateLimiters;
    private RateLimitAlgorithm currentAlgorithm;

    public RateLimiterService(RateLimitAlgorithm algorithm) {
        this.userRateLimiters = new ConcurrentHashMap<>();
        this.currentAlgorithm = algorithm;
    }
    
    /**
     * Allows switching algorithms at runtime.
     * Clears the cache to apply the new strategy immediately.
     */
    public void setAlgorithm(RateLimitAlgorithm algorithm) {
        this.currentAlgorithm = algorithm;
        this.userRateLimiters.clear();
        System.out.println("Switched algorithm to: " + algorithm);
    }
    
    /**
     * Processes the API request for a given user.
     * Throws an exception if the limit is exceeded.
     */
    public void processRequest(String userId) {
        // computeIfAbsent is atomic and thread-safe
        RateLimiter rateLimiter = userRateLimiters.computeIfAbsent(
            userId, 
            key -> RateLimiterFactory.createRateLimiter(currentAlgorithm)
        );

        if (!rateLimiter.tryAcquire()) {
            throw new RateLimitExceededException("HTTP 429: Rate limit exceeded for user " + userId + ". Please try again later.");
        }
        
        // If we reach here, the request is allowed
        System.out.println("Request allowed for user: " + userId + " [" + Thread.currentThread().getName() + "]");
    }

}
