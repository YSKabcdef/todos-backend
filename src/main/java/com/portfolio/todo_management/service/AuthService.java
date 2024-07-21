package com.portfolio.todo_management.service;

import com.portfolio.todo_management.dto.LoginDto;
import com.portfolio.todo_management.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
    String login(LoginDto loginDto);
}
