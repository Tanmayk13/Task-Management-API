package com.tanmay.day12.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserDto {
    @NotBlank(message = "Username is required!")
    private String userName;
}
