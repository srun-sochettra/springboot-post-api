package com.example.springhomework002.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    private Integer id;

    private String title;

    private String content;

    private String author;

    private LocalDateTime creationDate;

    private List<String> tags;
}