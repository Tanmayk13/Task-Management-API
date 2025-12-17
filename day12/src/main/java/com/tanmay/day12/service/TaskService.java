package com.tanmay.day12.service;

import com.tanmay.day12.dto.TaskDto;
import com.tanmay.day12.dto.TaskResponseDto;
import com.tanmay.day12.entity.Task;
import com.tanmay.day12.exception.TaskNotFoundException;
import com.tanmay.day12.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repo;

    public Page<@NonNull TaskResponseDto> getTasks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").descending());

        return repo.findAll(pageable)
                .map(task -> new TaskResponseDto(
                        task.getId(),
                        task.getName(),
                        task.getDescription(),
                        task.getCompleted(),
                        task.getUser() != null ? task.getUser().getId() : null
                ));
    }

    public Page<@NonNull TaskResponseDto> getCompletedTasks(boolean completed, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<@NonNull Task> taskPage = repo.findByCompleted(completed, pageable);

        return taskPage.map(task -> new TaskResponseDto(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getCompleted(),
                task.getUser() != null ? task.getUser().getId() : null
        ));
    }


    public TaskResponseDto createTask(TaskDto dto) {
        Task task = mapToEntity(dto);
        Task savedTask = repo.save(task);

        return new TaskResponseDto(
                savedTask.getId(),
                savedTask.getName(),
                savedTask.getDescription(),
                savedTask.getCompleted(),
                savedTask.getUser() != null ? savedTask.getUser().getId() : null
        );
    }

    public void deleteTask(Integer id) {
        if (!repo.existsById(id)) {
            throw new TaskNotFoundException("Task not found");
        }
        repo.deleteById(id);
    }

    public TaskResponseDto updateTask(Integer id, TaskDto dto) {
        Task existingTask = repo.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Task not found")
        );

        existingTask.setName(dto.getName());
        existingTask.setDescription(dto.getDescription());
        existingTask.setCompleted(dto.getCompleted());

        Task savedTask = repo.save(existingTask);

        return new TaskResponseDto(
                savedTask.getId(),
                savedTask.getName(),
                savedTask.getDescription(),
                savedTask.getCompleted(),
                savedTask.getUser() != null ? savedTask.getUser().getId() : null
        );
    }

    public TaskResponseDto getTaskById(Integer id) {
        Task task = repo.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Task not found")
        );

        return new TaskResponseDto(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getCompleted(),
                task.getUser() != null ? task.getUser().getId() : null
        );
    }

    private Task mapToEntity(TaskDto dto) {
        Task task = new Task();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.getCompleted());
        return task;
    }
}
