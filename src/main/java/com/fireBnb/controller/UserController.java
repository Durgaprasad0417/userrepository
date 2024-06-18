package com.fireBnb.controller;

import com.fireBnb.dto.LoginDto;
import com.fireBnb.dto.PropertyUserDto;
import com.fireBnb.dto.TokenResponse;
import com.fireBnb.entity.PropertyUser;
import com.fireBnb.repository.PropertyUserRepository;
import com.fireBnb.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private UserService service;
    private PropertyUserRepository repository;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody PropertyUserDto dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        if (repository.existsByUsername(dto.getUsername())) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
        dto.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10))); // Hash password
        PropertyUserDto pdto = service.addUser(dto);
        return new ResponseEntity<>("Added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) { // Use @RequestBody
        String token = service.verifyLogin(loginDto);
        if (token!=null) {
            TokenResponse tokenResponse =new TokenResponse();
            tokenResponse.setToken(token);
            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>("Login failed", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/profile")
    public PropertyUser getCurrentUser(@AuthenticationPrincipal PropertyUser user) {
        return user;

    }
}
