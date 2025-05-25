package com.doorcs.schedule.service.response;

import java.sql.Date;

public record CreateScheduleResponse(Long userId, String content, Date createdDate) {
}
