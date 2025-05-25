package com.doorcs.schedule.service.response;

import java.sql.Date;

public record CreateUserResponse(String name, String email, Date createdDate) {
}
