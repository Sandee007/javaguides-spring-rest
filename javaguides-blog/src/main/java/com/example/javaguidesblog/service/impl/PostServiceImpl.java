package com.example.javaguidesblog.service.impl;

import com.example.javaguidesblog.dto.PostDto;
import com.example.javaguidesblog.entity.Post;
import com.example.javaguidesblog.exception.ResourceNotFoundException;
import com.example.javaguidesblog.payload.PostResponse;
import com.example.javaguidesblog.repository.PostRepository;
import com.example.javaguidesblog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private PostDto entityToDto(Post post) {
        return PostDto.builder()
                      .id(post.getId())
                      .title(post.getTitle())
                      .description(post.getDescription())
                      .content(post.getContent())
                      .build();
    }

    private Post dtoToEntity(PostDto postDto) {
        return Post.builder()
                   .title(postDto.getTitle())
                   .description(postDto.getDescription())
                   .content(postDto.getContent())
                   .build();
    }

    private Post findById(Long id) throws ResourceNotFoundException {
        return postRepository.findById(id)
                             .orElseThrow(
                                     () -> new ResourceNotFoundException("Post", "id", id.toString())
                             );
    }

    @Override
    public PostDto create(PostDto postDto) {

        //        * convert dto to entity
        Post post = dtoToEntity(postDto);

        //        * save entity
        post = postRepository.save(post);

        //        * convert entity to dto
        return entityToDto(post);

    }

    @Override
    public PostResponse index(int pageNo, int pageSize, String sortBy, String sortDirection) {

        //        * create pageable instance
        Pageable pageable = PageRequest.of(
                pageNo, pageSize, Sort.by(
                        sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name())
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC
                        , sortBy
                )
        );

        //        ! repo returns a page object now
        Page<Post> page = postRepository.findAll(pageable);

        //        * get content from page object
        List<Post> posts = page.getContent();

        List<PostDto> content = posts.stream().map(i -> entityToDto(i)).toList();

        return PostResponse.builder()
                           .content(content)
                           .pageNo(page.getNumber())
                           .pageSize(page.getSize())
                           .totalElements(page.getTotalElements())
                           .totalPages(page.getTotalPages())
                           .lastPage(page.isLast())
                           .build();
    }

    @Override
    public PostDto getById(Long id) {
        Post post = findById(id);
        return entityToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = findById(id);

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        postRepository.save(post);

        return entityToDto(post);
    }

    @Override
    public void deleteById(Long id) {
        Post post = findById(id);
        postRepository.delete(post);
    }
}
