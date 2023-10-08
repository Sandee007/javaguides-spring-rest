package com.example.javaguidesblog.service.impl;

import com.example.javaguidesblog.dto.CommentDto;
import com.example.javaguidesblog.entity.Comment;
import com.example.javaguidesblog.entity.Post;
import com.example.javaguidesblog.exception.BlogApiException;
import com.example.javaguidesblog.exception.ResourceNotFoundException;
import com.example.javaguidesblog.repository.CommentRepository;
import com.example.javaguidesblog.repository.PostRepository;
import com.example.javaguidesblog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    private Comment dtoToEntity(CommentDto commentDto) {
        return Comment.builder()
                      .name(commentDto.getName())
                      .email(commentDto.getEmail())
                      .body(commentDto.getBody())
                      .build();
    }

    private CommentDto entityToDto(Comment comment) {
        return CommentDto.builder()
                         .id(comment.getId())
                         .name(comment.getName())
                         .email(comment.getEmail())
                         .body(comment.getBody())
                         .build();
    }

    private Post findPostById(Long postId) {
        return postRepository
                .findById(postId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Post", "id", postId.toString())
                );
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository
                .findById(commentId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Comment", "id", commentId.toString())
                );
    }

    private Comment validateCommentBelongsToPost(Long postId, Long commentId) throws BlogApiException {
        Post post = findPostById(postId);
        Comment comment = findCommentById(commentId);

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the Post");
        }

        return comment;
    }

    @Override
    public CommentDto create(Long postId, CommentDto commentDto) {
        Comment comment = dtoToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId.toString())
        );
        comment.setPost(post);
        comment = commentRepository.save(comment);

        return entityToDto(comment);
    }

    @Override
    public List<CommentDto> findAllByPost_Id(Long postId) {
        List<Comment> comments = commentRepository.findAllByPost_Id(postId);
        return comments.stream()
                       .map(this::entityToDto)
                       .toList();
    }

    @Override
    public CommentDto findById(Long postId, Long commentId) throws ResourceNotFoundException, BlogApiException {
        // * will throw the exception if not belongs to
        Comment comment = validateCommentBelongsToPost(postId, commentId);
        return entityToDto(comment);
    }

    @Override
    public CommentDto update(
            Long postId,
            Long commentId,
            CommentDto commentDto
    ) throws ResourceNotFoundException, BlogApiException {
        Comment comment = validateCommentBelongsToPost(postId, commentId);

        comment.setId(commentId);
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        comment = commentRepository.save(comment);
        return entityToDto(comment);
    }

    @Override
    public void delete(Long postId, Long commentId) {
        Comment comment = validateCommentBelongsToPost(postId, commentId);
        commentRepository.delete(comment);
    }
}
