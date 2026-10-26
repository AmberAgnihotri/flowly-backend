package com.flowly.flowlybackend.repository;

import com.flowly.flowlybackend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectBoardId(Long projectBoardId);
}