package com.lld.eventbus;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Boot up the advanced engine
        EventBus eventBus = new AsyncEventBus();

        // 1. Setup our dummy modules
        //Subscriber inventory = new InventorySubscriber();
        Subscriber faulty = new FaultySubscriber();

        // Both modules are listening to the same topic
        eventBus.subscribe("ORDER_CONFIRMED", new InventorySubscriber());
        eventBus.subscribe("ORDER_CONFIRMED", faulty);

        System.out.println("=== SCENARIO 1: Causal Ordering (Thread Hashing) ===");
        // These two events share the exact same Partition Key ("ORD-777").
        // Watch the console output: they will always be executed by the EXACT same thread.
        eventBus.publish(new OrderConfirmedEvent("ORD-777", "Step 1: Payment"));
        eventBus.publish(new OrderConfirmedEvent("ORD-777", "Step 2: Shipping"));
        
        // This event has a different key ("ORD-999") and may be picked up by a different thread in parallel.
        eventBus.publish(new OrderConfirmedEvent("ORD-999", "Step 1: Payment"));
        
        Thread.sleep(500); // Pause to let async threads print before moving to next scenario


        System.out.println("\n=== SCENARIO 2: Idempotency (Duplicate Event Filtering) ===");
        // Imagine a user double-clicks the "Checkout" button, sending the exact same event twice.
        Event doubleClickEvent = new OrderConfirmedEvent("ORD-555", "Duplicate Test");
        
        System.out.println("Publishing first click...");
        eventBus.publish(doubleClickEvent);
        
        System.out.println("Publishing second click...");
        eventBus.publish(doubleClickEvent); // The bus should catch this and drop it safely
        
        Thread.sleep(500);


        System.out.println("\n=== SCENARIO 3: Retries and Dead Letter Queue (DLQ) ===");
        // The Faulty module is programmed to crash when it sees "ORD-ERROR".
        // Watch the console as it fails, waits 1 second, retries up to 3 times, and then moves to the DLQ.
        eventBus.publish(new OrderConfirmedEvent("ORD-ERROR", "Poison Pill Event"));

        // We must keep the main application running for a few seconds so we can actually see 
        // the background threads do their 3-second retry loop.
        Thread.sleep(4000); 
        
        System.out.println("\n=== Server Shutdown ===");
    }
}
    // ==========================================
    // DUMMY SUBSCRIBERS FOR TESTING
    // ==========================================

//    static class InventorySubscriber implements Subscriber {
//        @Override
//        public String getSubscriberId() { 
//            return "INVENTORY_MODULE"; 
//        }
//
//        @Override
//        public void onEvent(Event event) {
//            // We print the Thread Name to prove Causal Ordering is working
//            String threadName = Thread.currentThread().getName();
//            System.out.println("[" + threadName + "] Inventory processed: " 
//                + event.getPartitionKey() + " -> " + event.getPayload());
//        }
//    }
//
//    static class FaultySubscriber implements Subscriber {
//        @Override
//        public String getSubscriberId() { 
//            return "FAULTY_MODULE"; 
//        }
//
//        @Override
//        public void onEvent(Event event) throws Exception {
//            // We intentionally crash this module ONLY for the ORD-ERROR test
//            if ("ORD-ERROR".equals(event.getPartitionKey())) {
//                System.out.println("[Faulty Module] Database locked! Throwing exception...");
//                throw new RuntimeException("Simulated Database Crash");
//            }
//        }
//    }
//}