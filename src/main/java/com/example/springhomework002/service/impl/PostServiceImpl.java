package com.example.springhomework002.service.impl;

import com.example.springhomework002.model.entity.Post;
import com.example.springhomework002.model.request.PostRequest;
import com.example.springhomework002.repository.PostRepository;
import com.example.springhomework002.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post insertPost(PostRequest request) {
        Post newPost = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(request.getAuthor())
                .creationDate(LocalDateTime.now())
                .tags(request.getTags())
                .build();

        return postRepository.save(newPost);
    }

    @Override
    public List<Post> getAllPosts(Integer page, Integer size) {
        if (page != null && size != null) {
            return postRepository.findWithPagination(page, size);
        }
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> getPostById(Integer id) {
        return postRepository.findById(id);
    }

    @Override
    public Optional<Post> getPostByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    @Override
    public List<Post> getPostsByAuthor(String author) {
        return postRepository.findByAuthor(author);
    }

    @Override
    public Optional<Post> updatePostById(Integer id, PostRequest request) {
        Post updatedData = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(request.getAuthor())
                .tags(request.getTags())
                .build();

        return postRepository.updateById(id, updatedData);
    }

    @Override
    public boolean deletePostById(Integer id) {
        return postRepository.deleteById(id);
    }

    @Override
    public List<Post> getPostsByMultipleTags(List<String> tags) {
        return postRepository.findByMultipleTags(tags);
    }
}