package com.lld.limiter;

public interface RateLimiter {

	/**
     * Attempts to acquire permission for a request.
     * @return true if allowed, false if limit exceeded.
     */
    boolean tryAcquire();
}
