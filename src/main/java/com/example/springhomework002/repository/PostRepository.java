package com.example.springhomework002.repository;

import com.example.springhomework002.model.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    List<Post> findAll();

    Optional<Post> findById(Integer id);

    Optional<Post> findByTitle(String title);

    List<Post> findByAuthor(String author);

    Optional<Post> updateById(Integer id, Post updatedPost);

    boolean deleteById(Integer id);

    List<Post> findByMultipleTags(List<String> tags);

    List<Post> findWithPagination(int page, int size);
}

@Repository
class InMemoryPostRepository implements PostRepository {

    private final List<Post> posts = new ArrayList<>();
    private Integer idCounter = 1;

    @Override
    public Post save(Post post) {
        post.setId(idCounter++);
        posts.add(post);
        return post;
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(posts);
    }

    @Override
    public Optional<Post> findById(Integer id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Post> findByTitle(String title) {
        return posts.stream()
                .filter(post -> post.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    @Override
    public List<Post> findByAuthor(String author) {
        return posts.stream()
                .filter(post -> post.getAuthor().equalsIgnoreCase(author))
                .toList();
    }

    @Override
    public Optional<Post> updateById(Integer id, Post updatedPost) {
        Optional<Post> existingPost = findById(id);

        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            post.setAuthor(updatedPost.getAuthor());
            post.setTags(updatedPost.getTags());
            return Optional.of(post);
        }

        return Optional.empty();
    }

    @Override
    public boolean deleteById(Integer id) {
        return posts.removeIf(post -> post.getId().equals(id));
    }

    @Override
    public List<Post> findByMultipleTags(List<String> tags) {
        return posts.stream()
                .filter(post -> post.getTags().stream()
                        .anyMatch(postTag -> tags.stream()
                                .anyMatch(requestedTag -> requestedTag.equalsIgnoreCase(postTag))))
                .toList();
    }

    @Override
    public List<Post> findWithPagination(int page, int size) {
        int zeroBasedPage = page - 1;
        int fromIndex = zeroBasedPage * size;

        if (fromIndex >= posts.size()) {
            return new ArrayList<>();
        }

        int toIndex = Math.min(fromIndex + size, posts.size());
        return new ArrayList<>(posts.subList(fromIndex, toIndex));
    }
}