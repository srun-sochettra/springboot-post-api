package com.example.springhomework002.controller;

import com.example.springhomework002.model.entity.Post;
import com.example.springhomework002.model.request.PostRequest;
import com.example.springhomework002.model.response.ApiResponse;
import com.example.springhomework002.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Post>> insertPost(@Valid @RequestBody PostRequest request) {
        Post createdPost = postService.insertPost(request);

        ApiResponse<Post> response = ApiResponse.<Post>builder()
                .message("This post was successfully created")
                .payload(createdPost)
                .status(HttpStatus.CREATED)
                .time(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Post>>> getAllPosts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page != null && page < 1) page = 1;
        if (size != null && size < 1) size = 1;

        List<Post> posts = postService.getAllPosts(page, size);

        ApiResponse<List<Post>> response = ApiResponse.<List<Post>>builder()
                .message("All posts have been found successfully")
                .payload(posts)
                .status(HttpStatus.OK)
                .time(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Post>> getPostById(@PathVariable Integer id) {
        Optional<Post> foundPost = postService.getPostById(id);

        if (foundPost.isPresent()) {
            ApiResponse<Post> response = ApiResponse.<Post>builder()
                    .message("This post has found successfully")
                    .payload(foundPost.get())
                    .status(HttpStatus.OK)
                    .time(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        }

        ApiResponse<Post> notFoundResponse = ApiResponse.<Post>builder()
                .message("This post has not been found")
                .payload(null)
                .status(HttpStatus.NOT_FOUND)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
    }

    @GetMapping("/search/title")
    public ResponseEntity<ApiResponse<Post>> getPostByTitle(@RequestParam String title) {
        Optional<Post> foundPost = postService.getPostByTitle(title);

        if (foundPost.isPresent()) {
            ApiResponse<Post> response = ApiResponse.<Post>builder()
                    .message("This post has found successfully")
                    .payload(foundPost.get())
                    .status(HttpStatus.OK)
                    .time(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        }

        ApiResponse<Post> notFoundResponse = ApiResponse.<Post>builder()
                .message("This post has not been found")
                .payload(null)
                .status(HttpStatus.NOT_FOUND)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
    }

    @GetMapping("/search/author")
    public ResponseEntity<ApiResponse<List<Post>>> getPostsByAuthor(@RequestParam String author) {
        List<Post> authorPosts = postService.getPostsByAuthor(author);

        if (!authorPosts.isEmpty()) {
            ApiResponse<List<Post>> response = ApiResponse.<List<Post>>builder()
                    .message("This post has found successfully")
                    .payload(authorPosts)
                    .status(HttpStatus.OK)
                    .time(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        }

        ApiResponse<List<Post>> notFoundResponse = ApiResponse.<List<Post>>builder()
                .message("No posts have been found for this author")
                .payload(null)
                .status(HttpStatus.NOT_FOUND)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Post>> updatePostById(
            @PathVariable Integer id,
            @Valid @RequestBody PostRequest request) {

        Optional<Post> updatedPost = postService.updatePostById(id, request);

        if (updatedPost.isPresent()) {
            ApiResponse<Post> response = ApiResponse.<Post>builder()
                    .message("You're update successfully")
                    .payload(updatedPost.get())
                    .status(HttpStatus.OK)
                    .time(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        }

        ApiResponse<Post> notFoundResponse = ApiResponse.<Post>builder()
                .message("The save with update not found id.")
                .payload(null)
                .status(HttpStatus.NOT_FOUND)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePostById(@PathVariable Integer id) {
        boolean isDeleted = postService.deletePostById(id);

        if (isDeleted) {
            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .message("Congratulation your delete is successfully")
                    .payload(null)
                    .status(HttpStatus.OK)
                    .time(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        }

        ApiResponse<Void> notFoundResponse = ApiResponse.<Void>builder()
                .message("The save with update not found id.")
                .payload(null)
                .status(HttpStatus.NOT_FOUND)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
    }

    @GetMapping("/search/tags")
    public ResponseEntity<ApiResponse<List<Post>>> getPostsByTags(
            @RequestParam List<String> tags) {

        List<Post> matchingPosts = postService.getPostsByMultipleTags(tags);

        if (!matchingPosts.isEmpty()) {
            ApiResponse<List<Post>> response = ApiResponse.<List<Post>>builder()
                    .message("Posts have been found successfully by tags")
                    .payload(matchingPosts)
                    .status(HttpStatus.OK)
                    .time(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        }

        ApiResponse<List<Post>> notFoundResponse = ApiResponse.<List<Post>>builder()
                .message("No posts have been found for the given tags")
                .payload(null)
                .status(HttpStatus.NOT_FOUND)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
    }
}