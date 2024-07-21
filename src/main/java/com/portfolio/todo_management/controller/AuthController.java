package com.portfolio.todo_management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.todo_management.dto.JwtDto;
import com.portfolio.todo_management.dto.LoginDto;
import com.portfolio.todo_management.dto.RegisterDto;
import com.portfolio.todo_management.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtDto> register(@RequestBody LoginDto loginDto) {
        
        String token = authService.login(loginDto);
        JwtDto jwtDto = new JwtDto();
        jwtDto.setAccessToken(token);
        return new ResponseEntity<>(jwtDto,HttpStatus.OK);
    }
    
}
