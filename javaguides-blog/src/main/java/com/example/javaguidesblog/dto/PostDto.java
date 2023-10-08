package com.example.javaguidesblog.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
//@Value // * https://projectlombok.org/features/Value
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private String content;
}
