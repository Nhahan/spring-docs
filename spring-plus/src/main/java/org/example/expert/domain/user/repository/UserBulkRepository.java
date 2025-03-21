package org.example.expert.domain.user.repository;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final int BATCH_SIZE = 1000;

    public void bulkInsert(List<User> users) {
        String sql = "INSERT INTO users (email, password, nickname, user_role) VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, users, BATCH_SIZE, (ps, user) -> {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNickname());
            ps.setString(4, user.getUserRole().name());
        });
    }
}
