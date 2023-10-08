package com.example.javaguidesblog.dto;

import lombok.*;

import java.util.Set;

//@Value // * https://projectlombok.org/features/Value
@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Set<CommentDto> comments;
}
