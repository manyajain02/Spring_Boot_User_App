package com.springboot.project.uber.User.App.services.Impl;

import com.springboot.project.uber.User.App.dto.DriverDto;
import com.springboot.project.uber.User.App.dto.RiderDto;
import com.springboot.project.uber.User.App.entities.Driver;
import com.springboot.project.uber.User.App.entities.Rating;
import com.springboot.project.uber.User.App.entities.Ride;
import com.springboot.project.uber.User.App.entities.Rider;
import com.springboot.project.uber.User.App.exceptions.ResourceNotFoundException;
import com.springboot.project.uber.User.App.exceptions.RuntimeConflictException;
import com.springboot.project.uber.User.App.repositories.DriverRepository;
import com.springboot.project.uber.User.App.repositories.RatingRepository;
import com.springboot.project.uber.User.App.repositories.RiderRepository;
import com.springboot.project.uber.User.App.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {


    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;

    @Override
    public DriverDto rateDriver(Ride ride,Integer rating) {

        Driver driver = ride.getDriver();

        Rating ratingObj = ratingRepository.findByRide(ride)
                 .orElseThrow(()-> new ResourceNotFoundException("Rating not found with id:" + ride.getId()));

        if(ratingObj.getDriverRating()!=null){
            throw new RuntimeConflictException("Driver has already been rated ,cannot rate again");
        }
        ratingObj.setDriverRating(rating);

        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByDriver(driver).stream().mapToDouble(rating1 -> rating1.getDriverRating())
                .average().orElse(0.0);

        driver.setRating(newRating);

        Driver savedDriver = driverRepository.save(driver);
        return modelMapper.map(savedDriver, DriverDto.class);
    }

    @Override
    public RiderDto rateRider(Ride ride, Integer rating) {

        Rider rider = ride.getRider();
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(()-> new ResourceNotFoundException("Rating not found with id:" + ride.getId()));

        if(ratingObj.getRiderRating()!=null){
            throw new RuntimeConflictException("Rider has already been rated ,cannot rate again");
        }
        ratingObj.setRiderRating(rating);

        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(rating1 -> rating1.getRiderRating())
                .average().orElse(0.0);

        rider.setRating(newRating);

        Rider savedRider = riderRepository.save(rider);
        return modelMapper.map(savedRider, RiderDto.class);
    }
    public void createNewRating(Ride ride){
        Rating rating = Rating.builder()
                .rider(ride.getRider())
                .driver(ride.getDriver())
                .ride(ride)
                .build();
        ratingRepository.save(rating);

    }

}
