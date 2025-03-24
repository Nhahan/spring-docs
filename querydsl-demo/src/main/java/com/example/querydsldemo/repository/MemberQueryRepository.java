package com.example.querydsldemo.repository;

import com.example.querydsldemo.dto.MemberDto;
import com.example.querydsldemo.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberQueryRepository {
    // 예제 1. 전체 Member 조회
    List<Member> findAllMembers();

    // 예제 2. 특정 username으로 Member 조회
    List<Member> findByUsername(String username);

    // 예제 3. 나이가 주어진 값보다 큰 Member 조회
    List<Member> findMembersOlderThan(int age);

    // 예제 4. Member와 Team 조인하여 팀 이름으로 조회
    List<Member> findByTeamName(String teamName);

    // 예제 5. 동적 조건: username과 최소 나이 조건
    List<Member> findByUsernameAndMinAge(String username, Integer minAge);

    // 예제 6. 정렬 및 페이징 처리 (나이 내림차순 정렬)
    Page<Member> findMembersOrderByAgeDesc(Pageable pageable);

    // 예제 7. DTO 프로젝션: MemberDto에 Member 정보와 Team 이름 포함
    List<MemberDto> findMemberDto();
}
