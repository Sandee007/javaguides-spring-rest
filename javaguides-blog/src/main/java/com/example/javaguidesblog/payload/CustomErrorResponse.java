package com.example.javaguidesblog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorResponse {
    private Date timestamp;
    private String message;
    private String description;
}
