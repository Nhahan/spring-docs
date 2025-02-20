package com.example.demo.todo.entity;

import com.example.demo.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Todo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Todo(String content, Member member) {
        this.content = content;
        this.member = member;
    }

    public void update(String content) {
        this.content = content;
    }
}
