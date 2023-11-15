package com.yaman.todo.Controllers;


import com.yaman.todo.Dto.Impl.TaskRequest;
import com.yaman.todo.Dto.Impl.TaskResponseImpl;
import com.yaman.todo.Dto.TaskResponse;
import com.yaman.todo.Model.Task;
import com.yaman.todo.Services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestController {

    private final TaskService taskService;
    @GetMapping("/tasks")
    public ResponseEntity<Page<Task>> getAllTasks(Pageable pageable) {
        Page<Task> tasks = taskService.findAll(pageable);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<TaskRequest> getTask(@PathVariable Long taskId) {
        // Assuming TaskService has a method to find a task by ID
        Optional<Task> optionalTask = taskService.findById(taskId);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            TaskRequest taskRequest = new TaskRequest(task.getTask(), task.getDeadline());
            // Assuming TaskResponse is a class representing the response
            return new ResponseEntity<>(taskRequest, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskRequest taskRequest
    ) {

        Optional<Task> optionalTask = taskService.findById(taskId);

        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();

            existingTask.setTask(taskRequest.getTask());
            existingTask.setDeadline(taskRequest.getDeadline());
            // Update other fields as needed

            // Save the updated task
            taskService.save(existingTask);

            // Create a response
            TaskResponse taskResponse = new TaskResponseImpl(true,"Task is updated");

            // Return a ResponseEntity with the updated task and HTTP status OK
            return new ResponseEntity<>(taskResponse, HttpStatus.OK);
        } else {
            System.out.println("is not found");
            // If the task with the specified ID is not found, return a 404 Not Found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable Long taskId) {
        try {
            boolean isDeleted = taskService.deleteTaskById(taskId);

            if (isDeleted) {
                return ResponseEntity.ok(new TaskResponseImpl(true,"Task deleted successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new TaskResponseImpl(false,"Task not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TaskResponseImpl(false,"Error deleting task"));
        }
    }
}
