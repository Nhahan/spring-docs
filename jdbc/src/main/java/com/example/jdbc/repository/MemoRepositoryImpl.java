package com.example.jdbc.repository;

import com.example.jdbc.entity.Memo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MemoRepositoryImpl implements MemoRepository {


    private final JdbcTemplate jdbcTemplate;

    public MemoRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Memo save(Memo memo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into memo(content) values(?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, memo.getContent());
            return ps;
        }, keyHolder);

        memo.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return memo;
    }


    @Override
    public Optional<Memo> findById(Long id) {
        List<Memo> memos = jdbcTemplate.query(
                "select * from memo where id = ?",
                (rs, rowNum) -> new Memo(rs.getLong("id"), rs.getString("content")),
                id
        );

        return memos.stream().findAny();
    }

    @Override
    public List<Memo> findAll() {
        return jdbcTemplate.query(
                "select * from memo",
                (rs, rowNum) ->
                        new Memo(rs.getLong("id"), rs.getString("content"))
        );
    }

    @Override
    public Memo updateContent(Long id, String content) {
        jdbcTemplate.update(
                "update memo set content = ? where id = ?",
                content,
                id
        );

        return jdbcTemplate.queryForObject(
                "select * from memo where id = ?",
                (rs, rowNum) -> new Memo(rs.getLong("id"), rs.getString("content")),
                id
        );
    }

    @Override
    public void deleteById(Memo memo) {
        jdbcTemplate.update(
                "delete from memo where id = ?",
                memo.getId()
        );
    }
}
