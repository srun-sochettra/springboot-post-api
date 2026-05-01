package com.example.springhomework002.service;

import com.example.springhomework002.model.entity.Post;
import com.example.springhomework002.model.request.PostRequest;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Post insertPost(PostRequest request);

    List<Post> getAllPosts(Integer page, Integer size);

    Optional<Post> getPostById(Integer id);

    Optional<Post> getPostByTitle(String title);

    List<Post> getPostsByAuthor(String author);

    Optional<Post> updatePostById(Integer id, PostRequest request);

    boolean deletePostById(Integer id);

    List<Post> getPostsByMultipleTags(List<String> tags);
}