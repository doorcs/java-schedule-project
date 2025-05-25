package com.doorcs.schedule.service.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(String name, @NotBlank String password, @NotBlank @Email String email) {
}
