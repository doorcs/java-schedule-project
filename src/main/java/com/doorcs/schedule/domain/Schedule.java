package com.doorcs.schedule.domain;

import java.sql.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Schedule {

    private Long id;
    private String content;
    private String name;
    private String password;
    private Date createdAt;
    private Date modifiedAt;

    public static Schedule of(Long id, String content, String name, String password, Date createdAt, Date modifiedAt) {
        Schedule schedule = new Schedule();
        schedule.id = id;
        schedule.content = content;
        schedule.name = name;
        schedule.password = password;
        schedule.createdAt = createdAt;
        schedule.modifiedAt = modifiedAt;
        return schedule;
    }

    public static Schedule of(String content, String name, String password, Date createdAt) {
        return of(null, content, name, password, createdAt, createdAt);
    }

    public void update(String content, String name) {
        this.content = content;
        this.name = name;
        this.modifiedAt = new Date(System.currentTimeMillis());
    }
}
