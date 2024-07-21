package com.portfolio.todo_management.service;

import java.util.List;

import com.portfolio.todo_management.dto.TodoDto;

public interface TodoService {
    TodoDto addTodo(TodoDto todoDto);
    TodoDto getTodo(Long id);
    List<TodoDto> getAllTodo();
    TodoDto updateTodo(TodoDto todoDto, Long id);
    void deleteTodo(Long id);
    TodoDto completeTodo(Long id);
    TodoDto inCompleteTodo(Long id);
} 