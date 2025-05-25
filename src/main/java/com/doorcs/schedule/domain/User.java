package com.doorcs.schedule.domain;

import java.sql.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    private Long id;
    private String name;
    private String password;
    private String email;
    private Date createdAt;
    private Date modifiedAt;

    public static User of(Long id, String name, String password, String email, Date createdAt, Date modifiedAt) {
        User user = new User();
        user.id = id;
        user.name = name;
        user.password = password;
        user.email = email;
        user.createdAt = createdAt;
        user.modifiedAt = modifiedAt;
        return user;
    }

    public static User of(String name, String password, String email, Date createdAt) {
        return of(null, name, password, email, createdAt, createdAt);
    }

    public void update(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.modifiedAt = new Date(System.currentTimeMillis());
    }
}
