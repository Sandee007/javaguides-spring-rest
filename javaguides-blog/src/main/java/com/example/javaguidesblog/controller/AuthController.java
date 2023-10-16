package com.example.javaguidesblog.controller;

import com.example.javaguidesblog.dto.LoginDto;
import com.example.javaguidesblog.dto.RegisterDto;
import com.example.javaguidesblog.service.AuthService;
import com.example.javaguidesblog.util.ApiConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConfig.BASE + ApiConfig.Auth.BASE)
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {ApiConfig.Auth.LOGIN, "sign-in"})
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = {ApiConfig.Auth.REGISTER, "sign-up"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
