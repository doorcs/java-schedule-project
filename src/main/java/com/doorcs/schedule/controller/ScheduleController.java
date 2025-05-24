package com.doorcs.schedule.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doorcs.schedule.service.ScheduleService;
import com.doorcs.schedule.service.request.CreateScheduleRequest;
import com.doorcs.schedule.service.response.CreateScheduleResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> create(
        @RequestBody CreateScheduleRequest createScheduleRequest
    ) {
        CreateScheduleResponse createScheduleResponse = scheduleService.create(createScheduleRequest);

        return ResponseEntity.ok().body(createScheduleResponse);
    }
}
