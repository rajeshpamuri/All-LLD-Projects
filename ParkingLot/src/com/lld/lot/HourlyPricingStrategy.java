package com.lld.lot;

	public class HourlyPricingStrategy implements PricingStrategy {
	    private double hourlyRate;

	    public HourlyPricingStrategy(double rate) {
	        this.hourlyRate = rate;
	    }

	    @Override
	    public double calculatePrice(long hoursParked) {
	        return hoursParked * hourlyRate;
	    }
	}

