package com.springboot.project.uber.User.App.services.Impl;

import com.springboot.project.uber.User.App.entities.RideRequest;
import com.springboot.project.uber.User.App.exceptions.ResourceNotFoundException;
import com.springboot.project.uber.User.App.repositories.RideRequestRepository;
import com.springboot.project.uber.User.App.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;

    @Override
    public RideRequest findRideRequestById(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId).orElseThrow(()->new ResourceNotFoundException("RideRequest not found with id" + rideRequestId));

    }

    @Override
    public void update(RideRequest rideRequest) {
         rideRequestRepository.findById(rideRequest.getId())
                .orElseThrow(()-> new ResourceNotFoundException("RideRequest not found with id: " + rideRequest.getId()));
         rideRequestRepository.save(rideRequest);
    }
}
