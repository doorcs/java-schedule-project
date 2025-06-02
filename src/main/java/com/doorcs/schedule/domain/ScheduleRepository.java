package com.doorcs.schedule.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        String sql = "INSERT INTO schedule (content, user_id, created_at, modified_at) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
            schedule.getContent(),
            schedule.getUserId(),
            schedule.getCreatedAt(),
            schedule.getModifiedAt()
        );
    }

    public List<Map<String, Object>> findAllWithUser(Long userId, Date date, int page, int pageSize) {
        String sql = "SELECT s.id, s.user_id, s.content, s.created_at, s.modified_at, " +
                     "COALESCE(u.name, '탈퇴한 사용자') as user_name " +
                     "FROM schedule s " +
                     "JOIN user u ON s.user_id = u.id " + // Join 활용!
                     "WHERE TRUE";
        List<Object> params = new ArrayList<>();

        if (userId != null) {
            sql += " AND s.user_id = ?";
            params.add(userId);
        }

        if (date != null) {
            sql += " AND s.modified_at = ?";
            params.add(date);
        }

        sql += " ORDER BY s.modified_at LIMIT ? OFFSET ?";
        params.add(pageSize);
        params.add(page * pageSize);

        return jdbcTemplate.queryForList(sql, params.toArray()); // .query() 대신 .queryForList() 사용
    }

    public Schedule findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> Schedule.of(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("content"),
                rs.getDate("created_at"),
                rs.getDate("modified_at")
            ), id
        );
    }

    public int update(Schedule schedule) {
        String sql = "UPDATE schedule SET content = ?, modified_at = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
            schedule.getContent(),
            schedule.getModifiedAt(),
            schedule.getId()
        );
    }

    public int delete(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
