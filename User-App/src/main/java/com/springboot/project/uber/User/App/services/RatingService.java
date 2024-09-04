package com.springboot.project.uber.User.App.services;

import com.springboot.project.uber.User.App.dto.DriverDto;
import com.springboot.project.uber.User.App.dto.RiderDto;
import com.springboot.project.uber.User.App.entities.Ride;

public interface RatingService {

    DriverDto rateDriver(Ride ride, Integer rating);

    RiderDto rateRider(Ride ride, Integer rating);

    void createNewRating(Ride ride);
}
