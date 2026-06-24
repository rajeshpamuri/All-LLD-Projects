package com.lld.eventbus;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public class AsyncEventBus implements EventBus {

    private final Map<String, List<Subscriber>> routingTable;
    
    // REQUIREMENT 1: Causal Ordering (Thread array instead of one big pool)
    private final int THREAD_COUNT = 10;
    private final ExecutorService[] workers;
    
    // REQUIREMENT 2: Idempotency (Stores Event IDs we have already seen)
    private final Set<String> processedEvents;

    public AsyncEventBus() {
        this.routingTable = new ConcurrentHashMap<>();
        this.processedEvents = ConcurrentHashMap.newKeySet(); // Thread-safe Set
        this.workers = new ExecutorService[THREAD_COUNT];
        
        // Initialize 10 independent single-thread lanes for ordered processing
        for (int i = 0; i < THREAD_COUNT; i++) {
            this.workers[i] = Executors.newSingleThreadExecutor(); 
        }
    }


	@Override
    public void subscribe(String topic, Subscriber subscriber) {
        routingTable.putIfAbsent(topic, new CopyOnWriteArrayList<>());
        routingTable.get(topic).add(subscriber);
    }

    @Override
    public void unsubscribe(String topic, Subscriber subscriber) {
        List<Subscriber> subscribers = routingTable.get(topic);
        if (subscribers != null) {
            subscribers.remove(subscriber);
        }
    }

    @Override
    public void publish(Event event) {
        // REQUIREMENT 2: Idempotency Check
        // .add() returns false if the Set already contains this ID
        if (!processedEvents.add(event.getEventId())) {
            System.out.println("Idempotency triggered: Ignoring duplicate event " + event.getEventId());
            return; // Exit immediately, do not process again
        }

        String topic = event.getTopic();
        List<Subscriber> subscribers = routingTable.getOrDefault(topic, Collections.emptyList());

        // REQUIREMENT 1: Causal Ordering
        // Use the Partition Key (e.g., Order ID) to pick the exact thread lane
        int threadIndex = Math.abs(event.getPartitionKey().hashCode()) % THREAD_COUNT;

        for (Subscriber subscriber : subscribers) {
            
            // Submit to the SPECIFIC lane to guarantee order
            workers[threadIndex].submit(() -> {
                
                boolean success = false;
                int retries = 0;
                int maxRetries = 3;

                // REQUIREMENT 3: Retries on Failure
                while (!success && retries < maxRetries) {
                    try {
                        subscriber.onEvent(event);
                        success = true; // Breaks the loop if no exception
                    } catch (Exception e) {
                        retries++;
                        System.err.println("Attempt " + retries + " failed for subscriber " + subscriber.getSubscriberId());
                        try {
                            Thread.sleep(1000); // Wait 1 second before retrying
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }

                // REQUIREMENT 4: Dead Letter Queue (DLQ)
                if (!success) {
                    pushToDeadLetterQueue(event, subscriber);
                }
            });
        }
    }

    // Helper method for the DLQ requirement
    private void pushToDeadLetterQueue(Event event, Subscriber subscriber) {
        System.err.println("[DLQ] Event " + event.getEventId() + " failed permanently for " 
                + subscriber.getSubscriberId() + ". Pushing to Dead Letter Queue.");
        // In a real system, you would insert this into a DLQ database table here.
    }
}