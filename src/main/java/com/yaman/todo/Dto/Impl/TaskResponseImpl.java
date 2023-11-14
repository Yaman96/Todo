package com.yaman.todo.Dto.Impl;

import com.yaman.todo.Dto.TaskResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TaskResponseImpl implements TaskResponse {

    private boolean result;
    private String info;
}
