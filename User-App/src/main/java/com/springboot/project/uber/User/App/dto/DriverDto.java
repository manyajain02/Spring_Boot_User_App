package com.springboot.project.uber.User.App.dto;

import lombok.*;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {

    private Long id;

    private UserDto user;

    private Double rating;

    private Boolean available;

    private String vehicleId;
}

