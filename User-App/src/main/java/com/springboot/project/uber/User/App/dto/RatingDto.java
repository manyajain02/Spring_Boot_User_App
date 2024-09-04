package com.springboot.project.uber.User.App.dto;

import lombok.Data;

@Data
public class RatingDto {
    private Long rideId;
    private Integer rating;
}
