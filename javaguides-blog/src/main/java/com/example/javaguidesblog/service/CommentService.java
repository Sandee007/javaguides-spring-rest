package com.example.javaguidesblog.service;

import com.example.javaguidesblog.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto create(Long postId, CommentDto commentDto);
    List<CommentDto> findAllByPost_Id(Long postId);
    CommentDto findById(Long postId, Long commentId);
    CommentDto update(Long postId, Long commentId, CommentDto commentDto);
    void delete(Long postId, Long commentId);
}
