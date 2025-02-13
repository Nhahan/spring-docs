package com.example.demo2.comment.repository;

import com.example.demo2.comment.entity.Comment;
import com.example.demo2.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);
}
