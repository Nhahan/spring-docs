package com.example.post.controller;

import com.example.post.dto.*;
import com.example.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostSaveResponseDto> savePost(@RequestBody PostSaveRequestDto requestDto) {
        return ResponseEntity.ok(postService.savePost(requestDto));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostUpdateResponseDto> updatePost(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto) {
        return ResponseEntity.ok(postService.updatePost(id, requestDto));
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
