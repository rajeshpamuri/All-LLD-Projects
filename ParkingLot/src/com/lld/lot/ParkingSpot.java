package com.lld.lot;

import java.util.concurrent.locks.ReentrantLock;

public class ParkingSpot {
	private final String id;
    private final ParkingSpotType type;
    private boolean isFree;
    private Vehicle vehicle;
    private final ReentrantLock lock;
    public ParkingSpot(String id, ParkingSpotType type) {
        this.id = id;
        this.type = type;
        this.isFree = true;
        this.lock = new ReentrantLock();
    }

    // Attempt to park a vehicle here safely
    public boolean assignVehicle(Vehicle v) {
        lock.lock(); // Thread acquires lock for this specific spot
        try {
            if (isFree) {
                this.vehicle = v;
                this.isFree = false;
                return true; // Successfully parked!
            }
            return false; // Another thread took it just before us
        } finally {
            lock.unlock(); // Always release the lock in a finally block
        }
    }

    // Free up the spot when a vehicle leaves
    public void removeVehicle() {
        lock.lock();
        try {
            this.vehicle = null;
            this.isFree = true;
        } finally {
            lock.unlock();
        }
    }

    // Getters (Thread-safe reading for real-time dashboard)
    public boolean isFree() { 
    	return isFree;
    	}
    public ParkingSpotType getType() { 
    	return type; 
    	}
    public String getId() { 
    	return id; 
    	}
    public Vehicle getVehicle() {
    	return vehicle; 
    	}
}

