package com.example.post.repository;

import com.example.post.dto.PostResponseDto;
import com.example.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT new com.example.post.dto.PostResponseDto(p.id, GROUP_CONCAT(c)) FROM Post p LEFT JOIN Comment c ON p.id = c.post.id GROUP BY p.id")
    List<PostResponseDto> findAllWithComments();

}
