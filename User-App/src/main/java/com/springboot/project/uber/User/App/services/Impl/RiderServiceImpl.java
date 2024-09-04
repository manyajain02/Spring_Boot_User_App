package com.springboot.project.uber.User.App.services.Impl;

import com.springboot.project.uber.User.App.dto.DriverDto;
import com.springboot.project.uber.User.App.dto.RideDto;
import com.springboot.project.uber.User.App.dto.RideRequestDto;
import com.springboot.project.uber.User.App.entities.*;
import com.springboot.project.uber.User.App.entities.enums.RideRequestStatus;
import com.springboot.project.uber.User.App.entities.enums.RideStatus;
import com.springboot.project.uber.User.App.exceptions.ResourceNotFoundException;
import com.springboot.project.uber.User.App.repositories.RideRequestRepository;
import com.springboot.project.uber.User.App.repositories.RiderRepository;
import com.springboot.project.uber.User.App.services.DriverService;
import com.springboot.project.uber.User.App.services.RatingService;
import com.springboot.project.uber.User.App.services.RideService;
import com.springboot.project.uber.User.App.services.RiderService;
import com.springboot.project.uber.User.App.strategies.RideStrategyManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

   private final ModelMapper modelMapper;
   private final RideRequestRepository rideRequestRepository;
   private final RiderRepository riderRepository;
   private final RideStrategyManager rideStrategyManager;
   private final RideService rideService;
   private final DriverService driverService;
   private final RatingService ratingService;

   @Override
   @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
       Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
//        log.info(rideRequest.toString());
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);
        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);
       List<Driver> drivers = rideStrategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);
//      TODO: Send notifications to all the drivers about this ride request
        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();
        if(!rider.equals(ride.getRider()))
        {
            throw new RuntimeException("Rider does not own this ride with id: " + rideId);
        }
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be CANCELLED, invalid status: " + ride.getRideStatus());
        }
//        ride.getEndedAt(LocalDateTime.now());

        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(), true);

        return modelMapper.map(savedRide,RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();
        if(!rider.equals(ride.getRider()))
        {
            throw new RuntimeException("Rider is not a owner of this Ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException("Ride Status is not ENDED hence cannot be start rating, status:" + ride.getRideStatus());
        }
        return ratingService.rateDriver(ride, rating);
    }

    @Override
    public RideDto getMyProfile() {
        Rider currentRider = getCurrentRider();
        return modelMapper.map(currentRider,RideDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider, pageRequest)
                .map(ride -> modelMapper.map(ride,RideDto.class));
   }

    @Override
    public Rider createNewRide(User user) {
         Rider rider = Rider.builder()
                 .user(user)
                 .rating(0.0)
                 .build();
         return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return riderRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Rider not associated with user with id :" + user.getId()));
    }

}