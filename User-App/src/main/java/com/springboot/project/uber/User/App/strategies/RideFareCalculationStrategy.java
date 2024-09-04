package com.springboot.project.uber.User.App.strategies;

import com.springboot.project.uber.User.App.entities.RideRequest;

public interface RideFareCalculationStrategy {
    double RIDE_FARE_MULTIPLIER = 10;
    double calculateFare(RideRequest rideRequest);
}
