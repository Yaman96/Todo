package com.yaman.todo.Controllers;


import com.yaman.todo.Dto.Impl.TaskRequest;
import com.yaman.todo.Dto.Impl.TaskResponseImpl;
import com.yaman.todo.Dto.TaskResponse;
import com.yaman.todo.Model.Task;
import com.yaman.todo.Services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestController {

    private final TaskService taskService;
    @GetMapping("/tasks")
    public ResponseEntity<ArrayList<Task>> mainPage() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> addTask(@RequestBody TaskRequest taskRequest) {
        String task = taskRequest.getTask();
        LocalDateTime deadline = taskRequest.getDeadline();

        Task newTask = new Task(task,deadline);
        taskService.save(newTask);

        TaskResponse taskResponse = new TaskResponseImpl(true,"Task successfully created");
        return ResponseEntity.ok(taskResponse);
    }
}
