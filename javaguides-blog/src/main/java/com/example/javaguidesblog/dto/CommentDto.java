package com.example.javaguidesblog.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
public class CommentDto {
    private Long id;
    private String name;
    private String email;
    private String body;
}
