package com.doorcs.schedule.domain;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int save(User user) {
        String sql = "INSERT INTO user (name, password, email, created_at, modified_at) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
            user.getName(),
            user.getPassword(),
            user.getEmail(),
            user.getCreatedAt(),
            user.getModifiedAt()
        );
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> User.of(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getDate("created_at"),
                rs.getDate("modified_at")
            ), email
        );
    }
}
