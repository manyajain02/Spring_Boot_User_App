package com.springboot.project.uber.User.App.services;

import com.springboot.project.uber.User.App.dto.DriverDto;
import com.springboot.project.uber.User.App.dto.RideDto;
import com.springboot.project.uber.User.App.dto.RiderDto;
import com.springboot.project.uber.User.App.entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DriverService {

    RideDto acceptRide(Long rideId);

    RideDto cancelRide(Long rideId);

    RideDto startRide(Long rideId, String otp);

    RideDto endRide(Long rideId);

    RiderDto rateRider(Long rideId, Integer rating);

    DriverDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Driver getCurrentDriver();

    Driver updateDriverAvailability(Driver driver, boolean availability);

    Driver createNewDriver(Driver driver);
}
