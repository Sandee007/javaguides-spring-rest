package com.example.javaguidesblog.service.impl;

import com.example.javaguidesblog.dto.LoginDto;
import com.example.javaguidesblog.dto.RegisterDto;
import com.example.javaguidesblog.entity.Role;
import com.example.javaguidesblog.entity.User;
import com.example.javaguidesblog.exception.BlogApiException;
import com.example.javaguidesblog.repository.RoleRepository;
import com.example.javaguidesblog.repository.UserRepository;
import com.example.javaguidesblog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    //    * to use our own login functionality
    public AuthServiceImpl(
            AuthenticationManager authenticationManager, UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //     ! *******
    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "Login Successful";
    }

    @Override
    public String register(RegisterDto registerDto) {

        //         * check if username already exists
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username is already taken.");
        }

        //         * check if email already exists
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email is already taken.");
        }

        User user = User.builder()
                        .name(registerDto.getName())
                        .username(registerDto.getUsername())
                        .email(registerDto.getEmail())
                        .password(passwordEncoder.encode(registerDto.getPassword()))
                        .build();

        Set<Role> roles = new HashSet<>(
                List.of(
                        roleRepository.findByName("ROLE_USER").get()
                )
        );
        user.setRoles(roles);

        userRepository.save(user);
        return "Registered Successfully";
    }
}
