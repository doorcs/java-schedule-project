package com.doorcs.schedule.service.request;

public record SigninRequest(String email, String password) { // email 필드가 UNIQUE이므로 email, pw 사용
}
