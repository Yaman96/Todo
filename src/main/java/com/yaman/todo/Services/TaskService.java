package com.yaman.todo.Services;

import com.yaman.todo.Model.Task;
import com.yaman.todo.Repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;

    public ArrayList<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public void save(Task task) {
        taskRepository.save(task);
    }
}
