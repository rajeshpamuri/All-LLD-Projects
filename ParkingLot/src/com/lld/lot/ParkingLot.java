package com.lld.lot;

import java.util.List;
import java.util.Optional;

public class ParkingLot {
    private final List<ParkingSpot> spots;

    public ParkingLot(List<ParkingSpot> spots) {
        this.spots = spots;
    }

    // Logic to find an available spot based on VehicleType
    public Optional<ParkingSpot> findAvailableSpot(VehicleType type) {
        ParkingSpotType requiredType = mapVehicleToSpotType(type);
        return spots.stream()
                .filter(spot -> spot.isFree() && spot.getType() == requiredType)
                .findFirst();
    }

    private ParkingSpotType mapVehicleToSpotType(VehicleType type) {
        switch (type) {
            case BIKE: return ParkingSpotType.SMALL;
            case CAR: return ParkingSpotType.MEDIUM;
            case TRUCK: return ParkingSpotType.LARGE;
            default: throw new IllegalArgumentException("Unknown vehicle type");
        }
    }
}