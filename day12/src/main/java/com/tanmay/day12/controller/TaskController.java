package com.tanmay.day12.controller;

import com.tanmay.day12.dto.ApiResponse;
import com.tanmay.day12.dto.TaskDto;
import com.tanmay.day12.dto.TaskResponseDto;
import com.tanmay.day12.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "Task management API's")
public class TaskController {
    private final TaskService service;

    @PostMapping
    public ResponseEntity<@NonNull ApiResponse<TaskResponseDto>> createTask(@RequestBody @Valid TaskDto dto) {
        TaskResponseDto taskResponseDto = service.createTask(dto);
        return ResponseEntity.ok(
                ApiResponse.<TaskResponseDto>builder()
                        .success(true)
                        .message("Task Created")
                        .data(taskResponseDto)
                        .build()
        );
    }

    @GetMapping
    @Operation(summary = "Get all tasks")
    public ResponseEntity<@NonNull ApiResponse<Page<@NonNull TaskResponseDto>>> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<@NonNull TaskResponseDto> tasks = service.getTasks(page, size);
        return ResponseEntity.ok(
                ApiResponse.<Page<@NonNull TaskResponseDto>>builder()
                        .success(true)
                        .message("Fetched All tasks")
                        .data(tasks)
                        .build()
        );
    }

    @GetMapping("/completed")
    public ResponseEntity<@NonNull ApiResponse<Page<@NonNull TaskResponseDto>>> getCompletedTasks(
            @RequestParam(defaultValue = "false") boolean completed,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<@NonNull TaskResponseDto> completedTasks = service.getCompletedTasks(completed, page, size);
        return ResponseEntity.ok(ApiResponse.<Page<@NonNull TaskResponseDto>>builder()
                .success(true)
                .message("Fetched Completed tasks")
                .data(completedTasks)
                .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<@NonNull ApiResponse<TaskResponseDto>> updateTask(@PathVariable Integer id, @RequestBody @Valid TaskDto dto) {
        TaskResponseDto taskResponseDto = service.updateTask(id, dto);
        return ResponseEntity.ok(ApiResponse.<TaskResponseDto>builder()
                .success(true)
                .message("Updated task with Id: " + id)
                .data(taskResponseDto)
                .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<@NonNull Void> deleteTask(@PathVariable Integer id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NonNull ApiResponse<TaskResponseDto>> getTaskById(@PathVariable Integer id) {
        TaskResponseDto taskResponseDto = service.getTaskById(id);
        return ResponseEntity.ok(ApiResponse.<TaskResponseDto>builder()
                .success(true)
                .message("Task fetched by Id: " + id)
                .data(taskResponseDto)
                .build()
        );
    }
}
