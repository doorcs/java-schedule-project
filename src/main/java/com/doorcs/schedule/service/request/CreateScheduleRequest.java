package com.doorcs.schedule.service.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateScheduleRequest(@NotBlank @Size(max = 200) String content) {
}
