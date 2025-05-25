package com.doorcs.schedule.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.doorcs.schedule.service.response.DeleteScheduleResponse;
import com.doorcs.schedule.service.response.ReadScheduleResponse;
import com.doorcs.schedule.service.response.UpdateScheduleResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> create(
        @CookieValue String jwt,
        @Valid @RequestBody CreateScheduleRequest createScheduleRequest
    ) {
        CreateScheduleResponse createScheduleResponse = scheduleService.create(jwt, createScheduleRequest);

        return ResponseEntity.ok().body(createScheduleResponse);
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ReadScheduleResponse>> getAll(
        @RequestParam(required = false) Long userId,
        @RequestParam(required = false) Date modifiedDate,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5", name="pagesize") int pageSize
    ) {
        List<ReadScheduleResponse> readScheduleResponses = scheduleService.getAll(userId, modifiedDate, page, pageSize);

        return ResponseEntity.ok().body(readScheduleResponses);
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<ReadScheduleResponse> getById(
        @PathVariable Long id // Schedule의 ID!
    ) {
        ReadScheduleResponse readScheduleResponse = scheduleService.getById(id);

        return ResponseEntity.ok().body(readScheduleResponse);
    }

    @PutMapping("/schedules/{id}")
    public ResponseEntity<UpdateScheduleResponse> update(
        @CookieValue String jwt,
        @PathVariable Long id,
        @RequestBody UpdateScheduleRequest updateScheduleRequest
    ) {
        UpdateScheduleResponse updateScheduleResponse = scheduleService.update(jwt, id, updateScheduleRequest);

        return ResponseEntity.ok().body(updateScheduleResponse);
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<DeleteScheduleResponse> delete(
        @CookieValue String jwt,
        @PathVariable Long id
    ) {
        DeleteScheduleResponse deleted = scheduleService.delete(jwt, id);

        return ResponseEntity.ok().body(deleted);
    }
}
