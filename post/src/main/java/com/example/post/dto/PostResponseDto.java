package com.example.post.dto;

import com.example.post.entity.Comment;
import lombok.Getter;

import java.util.List;

@Getter
public class PostResponseDto {
    private final Long id;
    private final List<Comment> comments;

    public PostResponseDto(Long id, List<Comment> comments) {
        this.id = id;
        this.comments = comments;
    }
}
