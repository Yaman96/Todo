package com.yaman.todo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT")
    private long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String task;

    @Column(nullable = false)
    private LocalDateTime deadline;

    public Task(String task, LocalDateTime deadline) {
        this.task = task;
        this.deadline = deadline;
    }
}
