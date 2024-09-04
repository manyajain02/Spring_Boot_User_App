package com.springboot.project.uber.User.App.repositories;

import com.springboot.project.uber.User.App.entities.Payment;
import com.springboot.project.uber.User.App.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

   Optional<Payment> findByRide(Ride ride);
}