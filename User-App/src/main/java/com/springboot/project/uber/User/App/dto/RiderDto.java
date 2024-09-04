package com.springboot.project.uber.User.App.dto;

import com.springboot.project.uber.User.App.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderDto {

    private Long id;

    private User user;

    private Double rating;
}
