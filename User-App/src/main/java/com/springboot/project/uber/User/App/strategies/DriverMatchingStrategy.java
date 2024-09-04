package com.springboot.project.uber.User.App.strategies;

import com.springboot.project.uber.User.App.entities.Driver;
import com.springboot.project.uber.User.App.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {

    List<Driver> findMatchingDriver(RideRequest rideRequest);
}
