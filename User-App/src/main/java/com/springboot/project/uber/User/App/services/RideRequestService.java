package com.springboot.project.uber.User.App.services;

import com.springboot.project.uber.User.App.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long RideRequestId);

    void update(RideRequest rideRequest);
}
