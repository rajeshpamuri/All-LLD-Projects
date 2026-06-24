package com.lld.eventbus;


public class FaultySubscriber implements Subscriber {
    public String getSubscriberId() { return "FAULTY_MODULE"; }
    public void onEvent(Event event) throws Exception {
        System.out.println("[Faulty] Attempting to process...");
        throw new RuntimeException("Database lock timeout!"); 
    }
}
