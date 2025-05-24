package com.doorcs.schedule.service.response;

import java.sql.Date;

public record ReadScheduleResponse(String content, String name, Date modifiedDate) {
}
