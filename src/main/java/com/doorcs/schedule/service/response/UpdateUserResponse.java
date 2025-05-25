package com.doorcs.schedule.service.response;

import java.sql.Date;

public record UpdateUserResponse(String name, String email, Date modifiedDate) {
}
