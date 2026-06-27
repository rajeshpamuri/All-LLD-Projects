package com.lld.lot;

public class Car implements Vehicle {
	private String licensePlate;
	
	public Car(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	@Override
	public String getLicensePlate() {
		return licensePlate;
	}

	@Override
	public VehicleType getType() {
		// TODO Auto-generated method stub
		return VehicleType.CAR;
	}

}
