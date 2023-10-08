package com.example.javaguidesblog.payload;

import com.example.javaguidesblog.dto.PostDto;
import lombok.*;

import java.util.List;

@Builder
@Getter // * must have for jackson to work properly
public class PostResponse {
    private List<PostDto> content;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean lastPage;
}
