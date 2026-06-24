package com.lld.eventbus;

public class InventorySubscriber implements Subscriber {

    private final String subscriberId = "INVENTORY_SERVICE_01";

    @Override
    public String getSubscriberId() {
        return this.subscriberId;
    }

    @Override
    public void onEvent(Event event) throws Exception {
      
        if (!"ORDER_CONFIRMED".equals(event.getTopic())) {
            return;
        }

       
        String orderDetails = (String) event.getPayload();
        
       
        System.out.println("[Inventory] Processing deduction for: " + orderDetails);
        
        
        Thread.sleep(500); 
        System.out.println("[Inventory] Stock deducted successfully.");
    }
}
