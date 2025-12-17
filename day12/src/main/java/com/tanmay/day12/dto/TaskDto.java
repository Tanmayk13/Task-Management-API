package com.tanmay.day12.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TaskDto {
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @NotNull
    private Boolean completed;
}
