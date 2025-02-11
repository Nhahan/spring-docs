package com.example.post.service;

import com.example.post.dto.*;
import com.example.post.entity.Comment;
import com.example.post.entity.Post;
import com.example.post.repository.CommentRepository;
import com.example.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentSaveResponseDto saveComment(CommentSaveRequestDto requestDto) {
        Post post = postRepository.findById(requestDto.getPostId())
                .orElseThrow(() -> new IllegalStateException("Post not found with id: " + requestDto.getPostId()));

        Comment comment = new Comment(requestDto.getContent(), post);
        commentRepository.save(comment);
        return new CommentSaveResponseDto(comment.getId(), comment.getContent(), comment.getPost().getId());
    }

    @Transactional(readOnly = true)
    public CommentResponseDto getComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Comment not found with id: " + id));
        return new CommentResponseDto(comment.getId(), comment.getContent(), comment.getPost().getId());
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getAllComments() {
        return commentRepository.findAll().stream()
                .map(comment -> new CommentResponseDto(comment.getId(), comment.getContent(), comment.getPost().getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentUpdateResponseDto updateComment(Long id, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Comment not found with id: " + id));

        comment.updateContent(requestDto.getContent());
        return new CommentUpdateResponseDto(comment.getId(), comment.getContent());
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
