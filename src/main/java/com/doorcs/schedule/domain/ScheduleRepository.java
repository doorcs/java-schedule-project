package com.doorcs.schedule.domain;

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
}
