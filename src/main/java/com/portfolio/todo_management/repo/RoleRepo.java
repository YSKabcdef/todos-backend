package com.portfolio.todo_management.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.todo_management.entity.Role;

public interface RoleRepo extends JpaRepository<Role,Long>{
    Role findByName(String name);
}
