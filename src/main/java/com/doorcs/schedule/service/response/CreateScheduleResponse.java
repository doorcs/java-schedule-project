package com.doorcs.schedule.service.response;

import java.sql.Date;

public record CreateScheduleResponse(String content, String name, Date createdDate) {
}
