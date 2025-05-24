package com.doorcs.schedule.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    } // 이 생성자는 `@requiredArgsConstructor`로 대체 불가능!!

    public int save(Schedule schedule) {
        String sql = "INSERT INTO schedule (content, name, password, created_at, modified_at) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
            schedule.getContent(),
            schedule.getName(),
            schedule.getPassword(),
            schedule.getCreatedAt(),
            schedule.getModifiedAt()
        );
    }

    public List<Schedule> findAll(String name, Date date) {
        String sql = "SELECT * FROM schedule WHERE TRUE";
        List<Object> params = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            sql += " AND name = ?";
            params.add(name);
        }

        if (date != null) {
            sql += " AND created_at = ?";
            params.add(date);
        }

        sql += " ORDER BY modified_at";

        return jdbcTemplate.query(sql, (rs, rowNum) -> Schedule.of(
                rs.getLong("id"),
                rs.getString("content"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getDate("created_at"),
                rs.getDate("modified_at")
            ), params.toArray()
        );
    }

    public Schedule findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> Schedule.of(
                rs.getLong("id"),
                rs.getString("content"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getDate("created_at"),
                rs.getDate("modified_at")
            ), id
        );
    }
}
