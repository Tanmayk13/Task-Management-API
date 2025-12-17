package com.tanmay.day12.repository;

import com.tanmay.day12.entity.Task;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<@NonNull Task, @NonNull Integer> {
    Page<@NonNull Task> findByCompleted(boolean completed, Pageable pageable);
}
