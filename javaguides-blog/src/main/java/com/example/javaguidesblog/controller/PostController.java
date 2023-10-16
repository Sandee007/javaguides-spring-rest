package com.example.javaguidesblog.controller;

import com.example.javaguidesblog.dto.PostDto;
import com.example.javaguidesblog.payload.PostResponse;
import com.example.javaguidesblog.service.PostService;
import com.example.javaguidesblog.util.ApiConfig;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// * https://spring.io/guides/gs/rest-service-cors/
// * https://www.baeldung.com/spring-cors
@CrossOrigin(origins = "http://127.0.0.1:5173/")
@RestController
@RequestMapping(ApiConfig.BASE + ApiConfig.Posts.INDEX)
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')") // ! must need to have @EnableMethodSecurity in SecurityConfig
    @PostMapping()
    public ResponseEntity<PostDto> create(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.create(postDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public PostResponse index(
            @RequestParam(
                    value = "pageNo",
                    defaultValue = ApiConfig.Posts.IndexConfig.DEFAULT_PAGE_NUMBER
            ) int pageNo,
            @RequestParam(
                    value = "pageSize",
                    defaultValue = ApiConfig.Posts.IndexConfig.DEFAULT_PAGE_SIZE
            ) int pageSize,
            @RequestParam(
                    value = "sortBy",
                    defaultValue = ApiConfig.Posts.IndexConfig.DEFAULT_SORT_BY
            ) String sortBy,
            @RequestParam(
                    value = "sortDirection",
                    defaultValue = ApiConfig.Posts.IndexConfig.DEFAULT_SORT_DIRECTION
            ) String sortDirection
    ) {
        return postService.index(pageNo, pageSize, sortBy, sortDirection);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<PostDto> update(@PathVariable Long id, @Valid @RequestBody PostDto postDto) {
        PostDto dto = postService.updatePost(postDto, id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        postService.deleteById(id);
        return ResponseEntity.ok("Post deleted");
    }

}
