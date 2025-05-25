package com.doorcs.schedule.service.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SigninRequest(@NotBlank @Email String email, @NotBlank String password) { // UNIQUE인 email 필드와 pw 사용
}
