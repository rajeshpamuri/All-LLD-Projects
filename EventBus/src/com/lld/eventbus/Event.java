package com.lld.eventbus;

public interface Event {
	String getEventId(); 
    String getTopic();   
    Object getPayload();
	String getPartitionKey();
}
