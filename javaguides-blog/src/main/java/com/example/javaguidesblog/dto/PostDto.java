package com.example.javaguidesblog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

//@Value // * https://projectlombok.org/features/Value
@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Title should contain 2 characters at least")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Description should contain 10 characters at least")
    private String description;

    @NotEmpty
    @Size(min = 10, message = "Description should contain 10 characters at least")
    private String content;

    private Set<CommentDto> comments;
}
