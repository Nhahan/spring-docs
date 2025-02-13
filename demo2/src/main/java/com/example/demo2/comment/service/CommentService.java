package com.example.demo2.comment.service;

import com.example.demo2.comment.dto.CommentRequestDto;
import com.example.demo2.comment.dto.CommentResponseDto;
import com.example.demo2.comment.entity.Comment;
import com.example.demo2.comment.repository.CommentRepository;
import com.example.demo2.post.entity.Post;
import com.example.demo2.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto save(Long postId, CommentRequestDto dto) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("게시글 없음")
        );

        Comment comment = new Comment(post, dto.getContent());

        Comment saved = commentRepository.save(comment);
        return new CommentResponseDto(saved.getId(), post.getId(), saved.getContent());
    }
}
