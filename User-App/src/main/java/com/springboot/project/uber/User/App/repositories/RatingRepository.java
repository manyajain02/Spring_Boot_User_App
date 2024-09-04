package com.springboot.project.uber.User.App.repositories;

import com.springboot.project.uber.User.App.entities.Driver;
import com.springboot.project.uber.User.App.entities.Rating;
import com.springboot.project.uber.User.App.entities.Ride;
import com.springboot.project.uber.User.App.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByRider(Rider rider);
    List<Rating> findByDriver(Driver driver);

    Optional<Rating> findByRide(Ride ride);
}
