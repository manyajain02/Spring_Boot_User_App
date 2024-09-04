package com.springboot.project.uber.User.App.services;

import com.springboot.project.uber.User.App.dto.RideRequestDto;
import com.springboot.project.uber.User.App.entities.Driver;
import com.springboot.project.uber.User.App.entities.Ride;
import com.springboot.project.uber.User.App.entities.RideRequest;
import com.springboot.project.uber.User.App.entities.Rider;
import com.springboot.project.uber.User.App.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService   {

    Ride getRideById(Long rideId);

    void matchWithDriver(RideRequestDto rideRequestDto);

    Ride createNewRide(RideRequest rideRequest, Driver driver);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest);

}
