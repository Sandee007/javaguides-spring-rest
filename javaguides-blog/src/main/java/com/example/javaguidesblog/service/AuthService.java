package com.example.javaguidesblog.service;

import com.example.javaguidesblog.dto.LoginDto;
import com.example.javaguidesblog.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
