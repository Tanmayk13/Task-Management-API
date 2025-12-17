package com.tanmay.day12.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private final Integer id;
    private final String userName;
    private final List<TaskResponseDto> tasks;
    private final String password;
    private final String role;
}
