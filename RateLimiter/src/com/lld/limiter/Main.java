package com.lld.limiter;

public class Main {
   public static void main(String[] args) throws InterruptedException {
	// Initialize service with Fixed Window
       RateLimiterService service = new RateLimiterService(RateLimitAlgorithm.FIXED_WINDOW);
       
       System.out.println("--- Starting Simulation with FIXED_WINDOW ---");
       simulateTraffics simulateTraffics = new simulateTraffics();
       simulateTraffics.simulateTraffic(service);

       // Switch to Token Bucket at runtime
       System.out.println("\n--- Switching Configuration ---");
       service.setAlgorithm(RateLimitAlgorithm.TOKEN_BUCKET);
       
       System.out.println("\n--- Starting Simulation with TOKEN_BUCKET ---");
       simulateTraffics.simulateTraffic(service);
}
}
