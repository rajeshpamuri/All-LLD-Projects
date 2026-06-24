package com.lld.eventbus;

public class EmailSubscriber implements Subscriber {
    public String getSubscriberId() { return "EMAIL_MODULE"; }
    public void onEvent(Event event) {
        System.out.println("[Email] Sending order confirmation to customer.");
    }
}
