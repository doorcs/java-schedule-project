package com.doorcs.schedule.domain;

import java.sql.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Schedule {

    private Long id;
    private Long userId; // FK
    private String content;
    private Date createdAt;
    private Date modifiedAt;

    public static Schedule of(Long id, Long userId, String content, Date createdAt, Date modifiedAt) {
        Schedule schedule = new Schedule();
        schedule.id = id;
        schedule.userId = userId;
        schedule.content = content;
        schedule.createdAt = createdAt;
        schedule.modifiedAt = modifiedAt;
        return schedule;
    }

    public static Schedule of(Long userId, String content, Date createdAt) {
        return of(null, userId, content, createdAt, createdAt);
    }

    public void update(String content) {
        this.content = content;
        this.modifiedAt = new Date(System.currentTimeMillis());
    }
}
