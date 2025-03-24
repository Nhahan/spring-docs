package com.example.querydsldemo.repository;

import com.example.querydsldemo.dto.MemberDto;
import com.example.querydsldemo.entity.Member;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.querydsldemo.entity.QMember.member;
import static com.example.querydsldemo.entity.QTeam.team;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    // 예제 1. 전체 Member 조회
    @Override
    public List<Member> findAllMembers() {
        return queryFactory
                .selectFrom(member)
                .fetch();
    }

    // 예제 2. 특정 username으로 Member 조회
    @Override
    public List<Member> findByUsername(String username) {
        return queryFactory
                .selectFrom(member)
                .where(member.username.eq(username))
                .fetch();
    }

    // 예제 3. 나이가 주어진 값보다 큰 Member 조회
    @Override
    public List<Member> findMembersOlderThan(int age) {
        return queryFactory
                .selectFrom(member)
                .where(member.age.gt(age))
                .fetch();
    }

    // 예제 4. Member와 Team 조인하여 팀 이름으로 조회
    @Override
    public List<Member> findByTeamName(String teamName) {
        return queryFactory
                .selectFrom(member)
                .join(member.team, team)
                .where(team.name.eq(teamName))
                .fetch();
    }

    // 예제 5. 동적 조건: username과 최소 나이 조건
    @Override
    public List<Member> findByUsernameAndMinAge(String username, Integer minAge) {
        BooleanExpression usernameCond = (username != null && !username.isEmpty())
                ? member.username.eq(username)
                : null;
        BooleanExpression ageCond = (minAge != null)
                ? member.age.goe(minAge)
                : null;
        return queryFactory
                .selectFrom(member)
                .where(usernameCond, ageCond)
                .fetch();
    }

    // 예제 6. 정렬 및 페이징 처리 (나이 내림차순 정렬)
    @Override
    public Page<Member> findMembersOrderByAgeDesc(Pageable pageable) {
        List<Member> content = queryFactory
                .selectFrom(member)
                .orderBy(member.age.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(Wildcard.count)
                .from(member)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    // 예제 7. DTO 프로젝션: MemberDto에 Member 정보와 Team 이름 포함
    @Override
    public List<MemberDto> findMemberDto() {
        return queryFactory.select(
                        Projections.constructor(MemberDto.class,
                                member.id,
                                member.username,
                                team.name))
                .from(member)
                .join(member.team, team)
                .fetch();
    }
}
