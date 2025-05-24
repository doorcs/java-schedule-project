package com.doorcs.schedule.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doorcs.schedule.domain.Schedule;
import com.doorcs.schedule.domain.ScheduleRepository;
import com.doorcs.schedule.service.request.CreateScheduleRequest;
import com.doorcs.schedule.service.response.CreateScheduleResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponse create(CreateScheduleRequest createScheduleRequest) {
        int affectedRow = scheduleRepository.save(Schedule.of(
                // id는 auto increment
                createScheduleRequest.content(),
                createScheduleRequest.name(),
                createScheduleRequest.password(),
                createScheduleRequest.date()
                // modified_at 필드는 created_at 필드와 같은 값 사용
            )
        );

        if (affectedRow != 1) {
            throw new RuntimeException("Failed to create schedule");
        }

        return new CreateScheduleResponse(
            createScheduleRequest.content(),
            createScheduleRequest.name(),
            createScheduleRequest.date()
        );
    }
}
