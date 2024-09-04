package com.springboot.project.uber.User.App.repositories;

import com.springboot.project.uber.User.App.entities.Rider;
import com.springboot.project.uber.User.App.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByUser(User user);
}
