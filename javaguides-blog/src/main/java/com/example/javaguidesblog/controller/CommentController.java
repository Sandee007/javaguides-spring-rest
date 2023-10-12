package com.example.javaguidesblog.controller;

import com.example.javaguidesblog.dto.CommentDto;
import com.example.javaguidesblog.service.CommentService;
import com.example.javaguidesblog.util.ApiConfig;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConfig.BASE + ApiConfig.Comments.INDEX)
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping()
    public ResponseEntity<CommentDto> create(@PathVariable Long postId, @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.create(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<CommentDto> findAllByPost_Id(@PathVariable Long postId) {
        return commentService.findAllByPost_Id(postId);
    }

    @GetMapping("{commentId}")
    public ResponseEntity<CommentDto> findById(@PathVariable Long postId, @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.findById(postId, commentId));
    }

    @PutMapping("{commentId}")
    public ResponseEntity<CommentDto> update(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentDto commentDto
    ) {
        return ResponseEntity.ok(commentService.update(postId, commentId, commentDto));
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<String> delete(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.delete(postId, commentId);
        return ResponseEntity.ok("Comment Deleted");
    }
}
