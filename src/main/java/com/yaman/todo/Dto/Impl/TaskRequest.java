package com.yaman.todo.Dto.Impl;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskRequest {
    private String task;
    private LocalDateTime deadline;
}
