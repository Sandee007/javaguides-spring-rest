package com.example.javaguidesblog.service;

import com.example.javaguidesblog.dto.PostDto;
import com.example.javaguidesblog.payload.PostResponse;

public interface PostService {
    PostDto create(PostDto postDto);
    PostResponse index(int pageNo, int pageSize, String sortBy, String sortDirection);
    PostDto getById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deleteById(Long id);
}
