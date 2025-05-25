package com.doorcs.schedule.service.request;

import jakarta.validation.constraints.NotBlank;

public record DeleteUserRequest(@NotBlank String password) {
}
