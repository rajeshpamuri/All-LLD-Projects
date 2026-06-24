package com.lld.eventbus;

import java.util.UUID;

public class OrderConfirmedEvent implements Event {
    
    private final String eventId;
    private final String topic;
    private final Object payload;
    private final String orderId; // We use this for the Partition Key
    
    public OrderConfirmedEvent(String orderId,Object payload) {
       
        this.eventId = UUID.randomUUID().toString(); 
        this.topic = "ORDER_CONFIRMED";              
        this.payload = payload;
        this.orderId = orderId;
    }

    @Override
    public String getEventId() {
        return this.eventId;
    }

    @Override
    public String getTopic() {
        return this.topic;
    }

    @Override
    public Object getPayload() {
        return this.payload;
    }


    @Override
    public String getPartitionKey() { 
        return this.orderId; // The Bus hashes this exact string to pick the thread!
    }
}