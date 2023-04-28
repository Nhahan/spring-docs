package com.example.post.service;

import com.example.post.dto.PostResponseDto;
import com.example.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostResponseDto> getPosts() {
        return postRepository.findAllWithComments();
    }

}
