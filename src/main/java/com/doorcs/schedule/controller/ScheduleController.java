package com.doorcs.schedule.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doorcs.schedule.service.ScheduleService;
import com.doorcs.schedule.service.request.CreateScheduleRequest;
import com.doorcs.schedule.service.request.UpdateScheduleRequest;
import com.doorcs.schedule.service.response.CreateScheduleResponse;
import com.doorcs.schedule.service.response.ReadScheduleResponse;
import com.doorcs.schedule.service.response.UpdateScheduleResponse;

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

    @GetMapping("/schedules")
    public ResponseEntity<List<ReadScheduleResponse>> getAll(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Date date
    ) {
        List<ReadScheduleResponse> readScheduleResponses = scheduleService.getAll(name, date);

        return ResponseEntity.ok().body(readScheduleResponses);
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<ReadScheduleResponse> getById(
        @PathVariable Long id
    ) {
        ReadScheduleResponse readScheduleResponse = scheduleService.getById(id);

        return ResponseEntity.ok().body(readScheduleResponse);
    }

    @PutMapping("/schedules/{id}")
    public ResponseEntity<UpdateScheduleResponse> update(
        @PathVariable Long id,
        @RequestBody UpdateScheduleRequest updateScheduleRequest
    ) {
        UpdateScheduleResponse updateScheduleResponse = scheduleService.update(id, updateScheduleRequest);

        return ResponseEntity.ok().body(updateScheduleResponse);
    }
}
