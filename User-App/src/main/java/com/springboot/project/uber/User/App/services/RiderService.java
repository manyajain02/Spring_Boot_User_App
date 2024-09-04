package com.springboot.project.uber.User.App.services;

import com.springboot.project.uber.User.App.dto.DriverDto;
import com.springboot.project.uber.User.App.dto.RideDto;
import com.springboot.project.uber.User.App.dto.RideRequestDto;
import com.springboot.project.uber.User.App.entities.Rider;
import com.springboot.project.uber.User.App.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);

    DriverDto rateDriver(Long rideId, Integer rating);

    RideDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Rider createNewRide(User user);

    Rider getCurrentRider();
}
