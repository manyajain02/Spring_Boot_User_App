package com.springboot.project.uber.User.App.services.Impl;

import com.springboot.project.uber.User.App.dto.RideRequestDto;
import com.springboot.project.uber.User.App.entities.Driver;
import com.springboot.project.uber.User.App.entities.Ride;
import com.springboot.project.uber.User.App.entities.RideRequest;
import com.springboot.project.uber.User.App.entities.Rider;
import com.springboot.project.uber.User.App.entities.enums.RideRequestStatus;
import com.springboot.project.uber.User.App.entities.enums.RideStatus;
import com.springboot.project.uber.User.App.exceptions.ResourceNotFoundException;
import com.springboot.project.uber.User.App.repositories.RideRepository;
import com.springboot.project.uber.User.App.repositories.RideRequestRepository;
import com.springboot.project.uber.User.App.services.RideRequestService;
import com.springboot.project.uber.User.App.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService{

    private final RideRequestRepository rideRequestRepository;
    private final ModelMapper modelMapper;
    private final RideRequestService rideRequestService;
    private final RideRepository rideRepository;

    @Override
    public Ride getRideById(Long rideId) {
       return rideRepository.findById(rideId).orElseThrow(()-> new ResourceNotFoundException("Ride not found with id: " + rideId));
    }

    @Override
    public void matchWithDriver(RideRequestDto rideRequestDto) {

    }

    @Override
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);
        Ride ride = modelMapper.map(rideRequest, Ride.class);
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setDriver(driver);
        ride.setOtp(generateRandomOTP());
        ride.setId(null);

        rideRequestService.update(rideRequest);
        return rideRepository.save(ride);

    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest) {
        return rideRepository.findByRider(rider, pageRequest);

    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest) {
        return rideRepository.findByDriver(driver, pageRequest);
    }

    private String generateRandomOTP(){
        Random random = new Random();
        int otpInt = random.nextInt(10000); //0 to 9999
        return String.format("%04d", otpInt);
    }
}
