package com.springboot.project.uber.User.App.strategies.Impl;

import com.springboot.project.uber.User.App.entities.RideRequest;
import com.springboot.project.uber.User.App.services.DistanceService;
import com.springboot.project.uber.User.App.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RideFareDefaultFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;

    @Override
    public double calculateFare(RideRequest rideRequest) {
       double distance = distanceService.calculateDistance(rideRequest.getPickUpLocation(),
               rideRequest.getDropOffLocation());
        return distance*RIDE_FARE_MULTIPLIER;
    }
}
