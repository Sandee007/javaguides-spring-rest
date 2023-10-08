package com.example.javaguidesblog.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

//@Data
@Value // * https://projectlombok.org/features/Value
@Builder
public class PostDto {
    Long id;
    String title;
    String description;
    String content;
}
