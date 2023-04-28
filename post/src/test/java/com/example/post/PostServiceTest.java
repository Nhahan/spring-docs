package com.example.post;

import com.example.post.dto.PostResponseDto;
import com.example.post.entity.Comment;
import com.example.post.entity.Post;
import com.example.post.repository.CommentRepository;
import com.example.post.repository.PostRepository;
import com.example.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void beforeEacH() {
        Post post = postRepository.save(new Post());
        Comment comment = new Comment(post);
        commentRepository.save(comment);
        commentRepository.save(comment);
        commentRepository.save(comment);
    }

    @Test
    void serviceTest() {
        List<PostResponseDto> PostResponseDto = postService.getPosts();
        assertThat(PostResponseDto.size()).isEqualTo(1);
        assertThat(PostResponseDto.get(0).getComments().size()).isEqualTo(2);
    }
}
