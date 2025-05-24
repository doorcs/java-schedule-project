package com.doorcs.schedule.service.request;

import java.sql.Date;

public record CreateScheduleRequest(String content, String name, String password, Date date) {
}
