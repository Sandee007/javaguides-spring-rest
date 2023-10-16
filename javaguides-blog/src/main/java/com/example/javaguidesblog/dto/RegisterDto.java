package com.example.javaguidesblog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegisterDto {
    private String name;
    private String username;
    private String email;
    private String password;
}
