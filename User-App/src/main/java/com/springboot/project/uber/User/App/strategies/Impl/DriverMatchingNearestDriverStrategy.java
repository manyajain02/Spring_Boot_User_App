package com.springboot.project.uber.User.App.strategies.Impl;

import com.springboot.project.uber.User.App.entities.Driver;
import com.springboot.project.uber.User.App.entities.RideRequest;
import com.springboot.project.uber.User.App.repositories.DriverRepository;
import com.springboot.project.uber.User.App.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {
    private final DriverRepository driverRepository;
    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
//        return driverRepository.findMatchingDrivers(rideRequest.getPickUpLocation());
        return driverRepository.findTenNearestDrivers(rideRequest.getPickUpLocation());
    }
}
