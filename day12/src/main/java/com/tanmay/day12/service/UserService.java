package com.tanmay.day12.service;

import com.tanmay.day12.dto.TaskDto;
import com.tanmay.day12.dto.TaskResponseDto;
import com.tanmay.day12.dto.UserDto;
import com.tanmay.day12.dto.UserResponseDto;
import com.tanmay.day12.entity.Task;
import com.tanmay.day12.entity.User;
import com.tanmay.day12.exception.UserNotFoundException;
import com.tanmay.day12.repository.TaskRepository;
import com.tanmay.day12.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final TaskRepository taskRepo;

    public UserResponseDto createUser(UserDto dto) {
        User user = new User();
        user.setUserName(dto.getUserName());
        User savedUser = userRepo.save(user);
        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getUserName(),
                List.of(),
                savedUser.getPassword(),
                savedUser.getRole()
        );
    }

    public UserResponseDto getUserById(Integer id) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found!")
        );

        List<TaskResponseDto> tasks = user.getTasks().stream().map(
                task ->
                        new TaskResponseDto(
                                task.getId(),
                                task.getName(),
                                task.getDescription(),
                                task.getCompleted(),
                                task.getUser() != null ? task.getUser().getId() : null
                        )
        ).toList();

        return new UserResponseDto(
                user.getId(),
                user.getUserName(),
                tasks,
                user.getPassword(),
                user.getRole()
        );
    }

    public TaskResponseDto assignTaskToUser(Integer id, TaskDto dto) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found!")
        );

        Task newTask = mapToTask(dto, user);
        Task savedTask = taskRepo.save(newTask);
        return new TaskResponseDto(
                savedTask.getId(),
                savedTask.getName(),
                savedTask.getDescription(),
                savedTask.getCompleted(),
                savedTask.getUser().getId()
        );
    }

    private Task mapToTask(TaskDto dto, User user) {
        Task task = new Task();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.getCompleted());
        task.setUser(user);
        return task;
    }

    // Solves N + 1 problem by fetching users and tasks in single fetch join query
    public List<UserResponseDto> getAllUsersWithTasks() {
        return userRepo.findAllWithTasks().stream().map(user -> {
            List<TaskResponseDto> tasks = user.getTasks().stream().map(task ->
                    new TaskResponseDto(
                            task.getId(),
                            task.getName(),
                            task.getDescription(),
                            task.getCompleted(),
                            task.getUser() != null ? task.getUser().getId() : null
                    )
            ).toList();

            return new UserResponseDto(
                    user.getId(),
                    user.getUserName(),
                    tasks,
                    user.getPassword(),
                    user.getRole()
            );
        }).toList();
    }
}
