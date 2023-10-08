package com.example.javaguidesblog.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CommentDto {
    Long id;
    String name;
    String email;
    String body;
}
