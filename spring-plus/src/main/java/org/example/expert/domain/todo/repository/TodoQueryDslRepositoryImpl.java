package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.TodoSearchDto;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class TodoQueryDslRepositoryImpl implements TodoQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        Todo result = queryFactory
                .selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(
                        todoIdEq(todoId)
                ).fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<TodoSearchDto> search(
            String title,
            String managerNickname,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Pageable pageable
    ) {
        List<TodoSearchDto> results = queryFactory
                .select(
                        Projections.constructor(
                                TodoSearchDto.class,
                                todo.id,
                                todo.title,
                                manager.countDistinct(),
                                comment.countDistinct()
                        )
                )
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.user, user)
                .leftJoin(todo.comments, comment)
                .where(
                        titleContains(title),
                        createdAtBetween(startAt, endAt),
                        managerNicknameContains(managerNickname)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(todo.id)
                .orderBy(todo.id.desc())
                .fetch();

        Long totalCount = queryFactory
                .select(Wildcard.count)
                .from(todo)
                .where(
                        titleContains(title),
                        createdAtBetween(startAt, endAt),
                        managerNicknameContains(managerNickname)
                ).fetchOne();

        return new PageImpl<>(results, pageable, totalCount != null ? totalCount : 0L);
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? todo.title.containsIgnoreCase(title) : null;
    }

    private BooleanExpression createdAtBetween(LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt != null && endAt != null) {
            return todo.createdAt.between(startAt, endAt);
        } else if (startAt != null) {
            return todo.createdAt.after(startAt);
        } else if (endAt != null) {
            return todo.createdAt.before(endAt);
        } else {
            return null;
        }
    }

    private BooleanExpression managerNicknameContains(String managerNickname) {
        return managerNickname != null ? user.nickname.containsIgnoreCase(managerNickname) : null;
    }

    private BooleanExpression todoIdEq(Long todoId) {
        return todoId != null ? todo.id.eq(todoId) : null;
    }
}
