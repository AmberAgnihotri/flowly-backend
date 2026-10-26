package com.flowly.flowlybackend.repository;

import com.flowly.flowlybackend.entity.ProjectBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectBoardRepository extends JpaRepository<ProjectBoard, Long> {
    List<ProjectBoard> findByOwnerId(Long ownerId);
}