package com.springboot.project.uber.User.App.services.Impl;


import com.springboot.project.uber.User.App.dto.DriverDto;
import com.springboot.project.uber.User.App.dto.RideDto;
import com.springboot.project.uber.User.App.dto.RiderDto;
import com.springboot.project.uber.User.App.entities.Driver;
import com.springboot.project.uber.User.App.entities.Ride;
import com.springboot.project.uber.User.App.entities.RideRequest;
import com.springboot.project.uber.User.App.entities.User;
import com.springboot.project.uber.User.App.entities.enums.RideRequestStatus;
import com.springboot.project.uber.User.App.entities.enums.RideStatus;
import com.springboot.project.uber.User.App.exceptions.ResourceNotFoundException;
import com.springboot.project.uber.User.App.repositories.DriverRepository;
import com.springboot.project.uber.User.App.services.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;
   private final RatingService ratingService;
    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId){
        RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);
        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("RideRequest cannot be accepted, status is "+ rideRequest.getRideRequestStatus());
        }

        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.getAvailable())
        {
            throw new RuntimeException("Driver cannot accept due to unavailability");
        }
        currentDriver.setAvailable(false);
        Driver savedDriver = updateDriverAvailability(currentDriver,false);
       Ride ride = rideService.createNewRide(rideRequest, savedDriver);
       return modelMapper.map(ride, RideDto.class);

    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if(!driver.equals(ride.getDriver()))
        {
            throw new RuntimeException("Driver cannot start a ride as he has not accepted ride earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be CANCELLED, invalid status:" + ride.getRideStatus());
        }

        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);
//        driver.setAvailable(true);
//        driverRepository.save(driver);
        updateDriverAvailability(driver , true);
        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public RideDto startRide(Long rideId, String otp) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if(!driver.equals(ride.getDriver()))
        {
            throw new RuntimeException("Driver cannot start a ride as he has not accepted ride earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride Status is not CONFIRMED hence cannot be started, status:" + ride.getRideStatus());
        }

        if(!otp.equals(ride.getOtp())){
            throw new RuntimeException("Otp is not valid, Otp:" + ride.getOtp());
        }

        ride.setStartedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);

        paymentService.createNewPayment(savedRide);
        ratingService.createNewRating(savedRide);
        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public RideDto endRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if(!driver.equals(ride.getDriver()))
        {
            throw new RuntimeException("Driver cannot ended a ride as he has not accepted ride earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.ONGOING)) {
            throw new RuntimeException("Ride Status is not ONGOING hence cannot be ended, status:" + ride.getRideStatus());
        }
        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
        updateDriverAvailability(driver, true);
        paymentService.processPayment(savedRide);
        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if(!driver.equals(ride.getDriver()))
        {
            throw new RuntimeException("Driver is not a owner of this Ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException("Ride Status is not ENDED hence cannot be start rating, status:" + ride.getRideStatus());
        }
        return ratingService.rateRider(ride,rating);

    }

    @Override
    public DriverDto getMyProfile() {
        Driver currentDriver = getCurrentDriver();
        return modelMapper.map(currentDriver, DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(currentDriver,pageRequest)
                .map(ride -> modelMapper.map(ride,RideDto.class));
    }

    @Override
    public Driver getCurrentDriver(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return driverRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Driver not associated with user with id :" + user.getId()));
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {
        driver.setAvailable(available);
        return driverRepository.save(driver);
    }

    @Override
    public Driver createNewDriver(Driver driver) {
        return driverRepository.save(driver);
    }
}
