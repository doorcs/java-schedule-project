package com.doorcs.schedule.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doorcs.schedule.service.UserService;
import com.doorcs.schedule.service.request.CreateUserRequest;
import com.doorcs.schedule.service.request.SigninRequest;
import com.doorcs.schedule.service.response.CreateUserResponse;
import com.doorcs.schedule.service.response.SigninResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<CreateUserResponse> createUser(
        @RequestBody CreateUserRequest createUserRequest
    ) {
        CreateUserResponse createUserResponse = userService.create(createUserRequest);

        return ResponseEntity.ok().body(createUserResponse);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<SigninResponse> signinUser(
        @RequestBody SigninRequest signinRequest
    ) {
        SigninResponse signinResponse = userService.signin(signinRequest);
        ResponseCookie responseCookie = ResponseCookie.from("jwt", signinResponse.jwt())
            .httpOnly(true)
            .path("/")
            .build(); // 세션 쿠키

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
            .body(signinResponse);
    }
}
