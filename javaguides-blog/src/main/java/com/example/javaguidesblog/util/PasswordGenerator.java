package com.example.javaguidesblog.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Scanner;

public class PasswordGenerator {
    public static void main(String[] args) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("user"));
        System.out.println(passwordEncoder.encode("admin"));
    }
}
