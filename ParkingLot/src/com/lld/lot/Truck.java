package com.lld.lot;

public class Truck implements Vehicle {
    private String licensePlate;

    public Truck(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public String getLicensePlate() {
        return this.licensePlate;
    }

    @Override
    public VehicleType getType() {
        return VehicleType.TRUCK;
    }
}
