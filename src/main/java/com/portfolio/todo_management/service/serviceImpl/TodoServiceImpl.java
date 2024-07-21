package com.portfolio.todo_management.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.portfolio.todo_management.dto.TodoDto;
import com.portfolio.todo_management.entity.Todo;
import com.portfolio.todo_management.exception.ResourceNotFoundException;
import com.portfolio.todo_management.repo.TodoRepo;
import com.portfolio.todo_management.service.TodoService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService{
    private TodoRepo todoRepo;
    private ModelMapper modelMapper;
    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        Todo todo = modelMapper.map(todoDto,Todo.class);
        Todo savedTodo = todoRepo.save(todo);
        
        return modelMapper.map(savedTodo,TodoDto.class);
    }
    @Override
    public TodoDto getTodo(Long id) {
        Todo todo = todoRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("ID: " + id + " cannot be found in the database."));
        return modelMapper.map(todo, TodoDto.class);
    }
    @Override
    public List<TodoDto> getAllTodo() {
        List<Todo> todos = todoRepo.findAll();
        List<TodoDto> todoDtos = todos.stream().map(todoDto -> modelMapper.map(todoDto, TodoDto.class)).collect(Collectors.toList());
        return todoDtos;
    }
    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {
        Todo todo = todoRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("ID: " + id + " cannot be found in the database."));
        todo.setCompleted(todoDto.isCompleted());
        todo.setDescription(todoDto.getDescription());
        todo.setTitle(todoDto.getTitle());
        Todo savedTodo = todoRepo.save(todo);
        return modelMapper.map(savedTodo,TodoDto.class);
        
        }
    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("ID: " + id + " cannot be found in the database."));
        todoRepo.delete(todo);
    }
    @Override
    public TodoDto completeTodo(Long id) {
        Todo todo = todoRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("ID: " + id + " cannot be found in the database."));
        todo.setCompleted(Boolean.TRUE);
        TodoDto todoDto = modelMapper.map(todoRepo.save(todo),TodoDto.class);
        return todoDto;
    }
    @Override
    public TodoDto inCompleteTodo(Long id) {
        Todo todo = todoRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("ID: " + id + " cannot be found in the database."));
        todo.setCompleted(Boolean.FALSE);
        TodoDto todoDto = modelMapper.map(todoRepo.save(todo),TodoDto.class);
        return todoDto;
    }


    
}
