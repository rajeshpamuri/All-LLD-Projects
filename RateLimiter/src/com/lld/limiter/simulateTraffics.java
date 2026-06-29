package com.lld.limiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class simulateTraffics {
	  
	public void simulateTraffic(RateLimiterService service) throws InterruptedException {
        // Simulating 3 concurrent users making rapid requests
        ExecutorService executor = Executors.newFixedThreadPool(10);
        String[] users = {"user_1", "user_2"};

        // We will bombard the system with 120 requests per user instantly 
        // to observe the 100-request limit enforcement.
        for (String user : users) {
            for (int i = 1; i <= 120; i++) {
                final int requestNum = i;
                executor.submit(() -> {
                    try {
                        service.processRequest(user);
                    } catch (RateLimitExceededException e) {
                        System.err.println("Request " + requestNum + " REJECTED: " + e.getMessage());
                    }
                });
            }
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}
