package com.doorcs.schedule.service.response;

import java.sql.Date;

public record UpdateScheduleResponse(String content, String name, Date modifiedAt) {
}
