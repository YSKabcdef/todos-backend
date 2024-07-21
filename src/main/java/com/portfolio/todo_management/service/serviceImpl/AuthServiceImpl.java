package com.portfolio.todo_management.service.serviceImpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.portfolio.todo_management.dto.LoginDto;
import com.portfolio.todo_management.dto.RegisterDto;
import com.portfolio.todo_management.entity.Role;
import com.portfolio.todo_management.entity.User;
import com.portfolio.todo_management.exception.TodoAPIException;
import com.portfolio.todo_management.repo.RoleRepo;
import com.portfolio.todo_management.repo.UserRepo;
import com.portfolio.todo_management.security.JwtTokenProvider;
import com.portfolio.todo_management.service.AuthService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public String register(RegisterDto registerDto) {
        // check existing username
        if(userRepo.existsByUsername(registerDto.getUsername())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST,"username already existed");
        }
        // check existing email
        if(userRepo.existsByEmail(registerDto.getEmail())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST,"email already existed");
        }
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setName(registerDto.getName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role = roleRepo.findByName("ROLE_USER");
        roles.add(role);

        user.setRoles(roles);

        userRepo.save(user);
        return "User registered successfully";
    }
    @Override
    public String login(LoginDto loginDto) {
        
        Authentication authenticate  =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            loginDto.getUsernameOrEmail(), 
            loginDto.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = jwtTokenProvider.generateToken(authenticate);
        
        return token;
    }
    
}
