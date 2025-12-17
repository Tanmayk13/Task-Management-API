package com.tanmay.day12.controller;

import com.tanmay.day12.dto.*;
import com.tanmay.day12.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users", description = "User Management API's")
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<@NonNull ApiResponse<UserResponseDto>> createUser(@RequestBody @Valid UserDto userDto) {
        UserResponseDto userResponseDto = service.createUser(userDto);
        return ResponseEntity.ok(
                ApiResponse.<UserResponseDto>builder()
                        .success(true)
                        .message("User Created")
                        .data(userResponseDto)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NonNull ApiResponse<UserResponseDto>> getUser(@PathVariable Integer id) {
        UserResponseDto userResponseDto = service.getUserById(id);
        return ResponseEntity.ok(ApiResponse.<UserResponseDto>builder()
                .success(true).message("User fetched")
                .data(userResponseDto)
                .build()
        );
    }

    @PostMapping("/{id}/tasks")
    public ResponseEntity<@NonNull ApiResponse<TaskResponseDto>> assignTaskToUser(@PathVariable Integer id, @RequestBody @Valid TaskDto dto) {
        TaskResponseDto assignedTaskResponseDto = service.assignTaskToUser(id, dto);
        return ResponseEntity.ok(ApiResponse.<TaskResponseDto>builder()
                .success(true)
                .message("Task assigned to user")
                .data(assignedTaskResponseDto)
                .build()
        );
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<@NonNull ApiResponse<List<UserResponseDto>>> getAllUsers() {
        List<UserResponseDto> userResponseDtoList = service.getAllUsersWithTasks();
        return ResponseEntity.ok(ApiResponse.<List<UserResponseDto>>builder()
                .success(true)
                .message("All users fetched")
                .data(userResponseDtoList)
                .build()
        );
    }
}
