package com.lld.eventbus;

public interface EventBus {
	
    void subscribe(String topic, Subscriber subscriber);

   
    void unsubscribe(String topic, Subscriber subscriber);

 
    void publish(Event event);
}
