package com.example.demo2.post.dto;

import com.example.demo2.comment.dto.CommentSimpleResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class PostDetailResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final List<CommentSimpleResponseDto> comments;

    public PostDetailResponseDto(Long id, String title, String content, List<CommentSimpleResponseDto> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.comments = comments;
    }
}
