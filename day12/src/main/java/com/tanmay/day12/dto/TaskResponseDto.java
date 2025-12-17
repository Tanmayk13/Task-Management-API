package com.tanmay.day12.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskResponseDto {
    private final Integer id;
    private final String name;
    private final String description;
    private final Boolean completed;
    private final Integer userId;
}
