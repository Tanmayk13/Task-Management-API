package com.tanmay.day12.repository;

import com.tanmay.day12.entity.User;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<@NonNull User, @NonNull Integer> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tasks")
    List<User> findAllWithTasks();

    Optional<User> findByUserName(String userName);
}
