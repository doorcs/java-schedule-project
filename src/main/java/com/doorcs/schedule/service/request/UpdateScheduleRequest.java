package com.doorcs.schedule.service.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateScheduleRequest(@NotBlank @Size(max = 200) String content) {
}
