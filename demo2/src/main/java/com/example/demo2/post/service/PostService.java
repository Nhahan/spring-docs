package com.example.demo2.post.service;

import com.example.demo2.comment.dto.CommentSimpleResponseDto;
import com.example.demo2.comment.entity.Comment;
import com.example.demo2.comment.repository.CommentRepository;
import com.example.demo2.post.dto.PostDetailResponseDto;
import com.example.demo2.post.dto.PostRequestDto;
import com.example.demo2.post.dto.PostSimpleResponseDto;
import com.example.demo2.post.entity.Post;
import com.example.demo2.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public PostSimpleResponseDto save(PostRequestDto dto) {

        Post post = new Post(dto.getTitle(), dto.getContent());
        Post saved = postRepository.save(post);
        return new PostSimpleResponseDto(saved.getId(), saved.getTitle(), saved.getContent());
    }

    // 다 건 조회기 때문에 굳이 댓글 정보가 필요 없겠죠. 게시판 목록은 보통 제목만 보여주니까!
    @Transactional(readOnly = true)
    public List<PostSimpleResponseDto> findAll() {

        List<Post> posts = postRepository.findAll();

        List<PostSimpleResponseDto> dtos = new ArrayList<>();
        for (Post post : posts) {
            dtos.add(new PostSimpleResponseDto(
                    post.getId(),
                    post.getTitle(),
                    post.getContent()
            ));
        }
        return dtos;
    }

    // 단 건 조회면 당연히 그 게시글에 들어간 상태겠죠
    @Transactional(readOnly = true)
    public PostDetailResponseDto findById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("게시글 없음미다")
        );

        List<Comment> comments = commentRepository.findByPost(post);

        List<CommentSimpleResponseDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentSimpleResponseDto commentSimpleResponseDto = new CommentSimpleResponseDto(
                    comment.getId(), comment.getContent()
            );

            commentDtos.add(commentSimpleResponseDto);
        }

        return new PostDetailResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                commentDtos
        );
    }
}
