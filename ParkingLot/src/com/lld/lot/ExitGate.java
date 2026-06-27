package com.lld.lot;

public class ExitGate {
    private PricingStrategy pricingStrategy;

    public ExitGate(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public double processExit(Ticket ticket, ParkingSpot spot) {
        long durationMillis = System.currentTimeMillis() - ticket.getEntryTime();
        long hours = Math.max(1, durationMillis / 3600000); // 1 hour minimum
        
        double cost = pricingStrategy.calculatePrice(hours);
        
        spot.removeVehicle();
        ticket.setStatus(TicketStatus.PAID);
        
        return cost;
    }
}