package com.springboot.project.uber.User.App.services.Impl;

import com.springboot.project.uber.User.App.dto.DriverDto;
import com.springboot.project.uber.User.App.dto.SignupDto;
import com.springboot.project.uber.User.App.dto.UserDto;
import com.springboot.project.uber.User.App.entities.Driver;
import com.springboot.project.uber.User.App.entities.User;
import com.springboot.project.uber.User.App.entities.enums.Role;
import com.springboot.project.uber.User.App.exceptions.ResourceNotFoundException;
import com.springboot.project.uber.User.App.exceptions.RuntimeConflictException;
import com.springboot.project.uber.User.App.repositories.UserRepository;
import com.springboot.project.uber.User.App.security.JWTService;
import com.springboot.project.uber.User.App.services.AuthService;
import com.springboot.project.uber.User.App.services.DriverService;
import com.springboot.project.uber.User.App.services.RiderService;
import com.springboot.project.uber.User.App.services.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.springboot.project.uber.User.App.entities.enums.Role.DRIVER;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public String[] login(String email, String password) {

       Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(email, password));
        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
       return new String[] {accessToken, refreshToken};

    }

    @Override
    @Transactional
    public UserDto Signup(SignupDto signupDto) {
     User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);
     if(user != null){
         throw new RuntimeConflictException("Cannot Signup, User already exists with email" + signupDto.getEmail());
     }
        User mappedUser =  modelMapper.map(signupDto,User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser = userRepository.save(mappedUser);
        //Creating user related entities
        riderService.createNewRide(savedUser);
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId, String vehicleId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("user not found with id " + userId));
        if(user.getRoles().contains(DRIVER)) {
            throw new RuntimeConflictException("User with id " + userId + "is already a Driver");
        }
        Driver createDriver = Driver.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();
        user.getRoles().add(DRIVER);
        userRepository.save(user);

        Driver savedDriver = driverService.createNewDriver(createDriver);
        return modelMapper.map(savedDriver, DriverDto.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with id "+ userId));

        return jwtService.generateAccessToken(user);
    }
}
