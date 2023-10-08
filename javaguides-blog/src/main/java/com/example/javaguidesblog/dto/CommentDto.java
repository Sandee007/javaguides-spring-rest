package com.example.javaguidesblog.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
    private Long id;
    private String name;
    private String email;
    private String body;
}
