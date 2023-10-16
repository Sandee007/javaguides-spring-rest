package com.example.javaguidesblog.service;

import com.example.javaguidesblog.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
