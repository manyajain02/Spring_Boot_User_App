package com.springboot.project.uber.User.App.services;

import com.springboot.project.uber.User.App.dto.DriverDto;
import com.springboot.project.uber.User.App.dto.SignupDto;
import com.springboot.project.uber.User.App.dto.UserDto;

public interface AuthService {

    String[] login(String email, String password);

    UserDto Signup(SignupDto signupDto);

    DriverDto onboardNewDriver(Long userId, String vehicleId);

    String refreshToken(String refreshToken);
}
