package com.lld.lot;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Setup: Create Parking Spots
        List<ParkingSpot> spots = Arrays.asList(
            new ParkingSpot("S1", ParkingSpotType.SMALL),
            new ParkingSpot("M1", ParkingSpotType.MEDIUM),
            new ParkingSpot("L1", ParkingSpotType.LARGE)
        );

        ParkingLot lot = new ParkingLot(spots);
        PricingStrategy pricing = new HourlyPricingStrategy(10.0); // 10 per hour
        
        EntranceGate entrance = new EntranceGate(lot);
        ExitGate exitGate = new ExitGate(pricing);

        // 2. Simulation: A Car arrives
        Vehicle myCar = new Car("KA-01-1234");
        System.out.println("Car arriving: " + myCar.getLicensePlate());

        // Park the car
        ParkingSpot assignedSpot = lot.findAvailableSpot(myCar.getType()).orElse(null);
        if (assignedSpot != null && assignedSpot.assignVehicle(myCar)) {
            Ticket ticket = new Ticket(myCar.getLicensePlate());
            System.out.println("Parked successfully in spot: " + assignedSpot.getId());

            // 3. Exit simulation: Processing exit
            System.out.println("Processing exit...");
            double fee = exitGate.processExit(ticket, assignedSpot);
            
            System.out.println("Total parking fee: $" + fee);
            System.out.println("Ticket status: " + ticket.getStatus());
        } else {
            System.out.println("No spot available!");
        }
    }
    
}