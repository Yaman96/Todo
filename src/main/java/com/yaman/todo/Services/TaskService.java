package com.yaman.todo.Services;

import com.yaman.todo.Model.Task;
import com.yaman.todo.Repositories.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;

    public Page<Task> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public void save(Task task) {
        taskRepository.save(task);
    }

    public Optional<Task> findById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    public boolean deleteTaskById(Long id) {
        if (!taskRepository.existsById(id)) {
            return false;
        }
        taskRepository.deleteById(id);
        return true;
    }
}
