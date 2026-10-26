package com.flowly.flowlybackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_board_id", nullable = false)
    private ProjectBoard projectBoard;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 300)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TaskStatus status = TaskStatus.BACKLOG;

    protected Task() {
    }

    public Task(ProjectBoard projectBoard, String title, String description) {
        this.projectBoard = projectBoard;
        this.title = title;
        this.description = description;
    }

    public Long getId() { return id; }
    public ProjectBoard getProjectBoard() { return projectBoard; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
}