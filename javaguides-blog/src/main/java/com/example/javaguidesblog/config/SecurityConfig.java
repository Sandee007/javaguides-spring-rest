package com.example.javaguidesblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity // ! ******
public class SecurityConfig {

    //    * custom user details services
    private UserDetailsService userDetailsService; // ! CustomUserDetailsService is used automatically , cuz it's an @Service

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //    * password encoder
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //     * authentication manager - automatically uses custom user details service and the password encoder
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    //  auth.anyRequest().authenticated()
                    auth
                            .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                            .requestMatchers("/api/auth/**").permitAll()
                            .anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

    //    @Bean
    //    public UserDetailsService userDetailsService() {
    //        UserDetails user = User.builder()
    //                               .username("user")
    //                               .password(passwordEncoder().encode("user"))
    //                               .roles("USER")
    //                               .build();
    //
    //        UserDetails admin = User.builder()
    //                                .username("admin")
    //                                .password(passwordEncoder().encode("admin"))
    //                                .roles("ADMIN")
    //                                .build();
    //
    //        return new InMemoryUserDetailsManager(user, admin);
    //    }
}
