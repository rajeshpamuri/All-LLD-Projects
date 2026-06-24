package com.lld.eventbus;

public interface Subscriber {

	String getSubscriberId();
    
    void onEvent(Event event) throws Exception;
}
