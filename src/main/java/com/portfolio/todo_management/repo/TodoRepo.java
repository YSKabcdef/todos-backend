package com.portfolio.todo_management.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import com.portfolio.todo_management.entity.Todo;

public interface TodoRepo extends JpaRepository<Todo,Long>{
    
}
