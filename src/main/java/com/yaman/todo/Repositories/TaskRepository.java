package com.yaman.todo.Repositories;

import com.yaman.todo.Model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task,Long> {

    @Query(value = "SELECT t FROM Task t")
    ArrayList<Task> getAllTasks();
}
