package com.example.basic.service;

import com.example.basic.entity.Member;
import com.example.basic.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void insertAndGetMember() {
        Member member = new Member("김삿갓");
        memberRepository.save(member);

        List<Member> memberList = memberRepository.findAll();

        assertThat(memberList.get(0).getId()).isEqualTo(member.getId());
        assertThat(memberList.size()).isEqualTo(1);

        System.out.println("---------------");
        System.out.println("Member name: " + member.getName());
        System.out.println("Member id: " + member.getId());
        System.out.println("---------------");

        System.out.println("---------------");
        System.out.println("Member name: " + memberList.get(0).getName());
        System.out.println("Member id: " + memberList.get(0).getId());
        System.out.println("---------------");
    }

    @Test
    void insertMember() {
        Member member = new Member("김삿갓");
        memberRepository.save(member);

        assertThat(member.getId()).isEqualTo(1L);

        System.out.println("---------------");
        System.out.println("Member name: " + member.getName());
        System.out.println("Member id: " + member.getId());
        System.out.println("---------------");
    }
}