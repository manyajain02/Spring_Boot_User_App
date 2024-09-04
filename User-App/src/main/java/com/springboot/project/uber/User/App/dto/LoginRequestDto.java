package com.springboot.project.uber.User.App.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class LoginRequestDto {

    private String email;
    private String password;
}
