package com.doorcs.schedule.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doorcs.schedule.service.UserService;
import com.doorcs.schedule.service.request.CreateUserRequest;
import com.doorcs.schedule.service.response.CreateUserResponse;

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
}
