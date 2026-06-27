package com.lld.lot;

public class EntranceGate {
    private ParkingLot parkingLot;

    public EntranceGate(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        return parkingLot.findAvailableSpot(vehicle.getType())
                .map(spot -> {
                    if (spot.assignVehicle(vehicle)) {
                        return new Ticket(vehicle.getLicensePlate());
                    }
                    return null;
                }).orElse(null);
    }
}