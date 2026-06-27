package com.lld.lot;

public class Bike implements Vehicle {

	private String licensePlate;

    public Bike(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public String getLicensePlate() {
        return this.licensePlate;
    }

    @Override
    public VehicleType getType() {
        return VehicleType.BIKE;
    }

}
