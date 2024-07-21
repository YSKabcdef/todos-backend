package com.portfolio.todo_management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.todo_management.dto.TodoDto;
import com.portfolio.todo_management.service.TodoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/todo")
@AllArgsConstructor
public class TodoController {

    private TodoService todoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
        TodoDto savedTodoDto = todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedTodoDto , HttpStatus.CREATED);

        
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> findTodo(@PathVariable("id") Long id){
        TodoDto findDto = todoService.getTodo(id);
        return ResponseEntity.ok(findDto);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> findAllTodo(){
        return ResponseEntity.ok(todoService.getAllTodo())  ;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/update")
    public ResponseEntity<TodoDto> updatedTodo(@PathVariable("id") Long id, @RequestBody TodoDto todoDto){
        return ResponseEntity.ok(todoService.updateTodo(todoDto, id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long id){
        todoService.deleteTodo(id);
        return ResponseEntity.ok("The Todo is successfully deleted.");
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long id){
        return ResponseEntity.ok(todoService.completeTodo(id));
       
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("/{id}/in-complete")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable("id") Long id){
        return ResponseEntity.ok(todoService.inCompleteTodo(id));
       
    }

}
