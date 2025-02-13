package com.example.demo2.post.controller;

import com.example.demo2.post.dto.PostDetailResponseDto;
import com.example.demo2.post.dto.PostRequestDto;
import com.example.demo2.post.dto.PostSimpleResponseDto;
import com.example.demo2.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public PostSimpleResponseDto save(@RequestBody PostRequestDto dto) {
        return postService.save(dto);
    }

    @GetMapping("/posts")
    public List<PostSimpleResponseDto> findAll() {
        return postService.findAll();
    }

    @GetMapping("/posts/{postId}")
    public PostDetailResponseDto findAll(@PathVariable Long postId) {
        return postService.findById(postId);
    }
}
