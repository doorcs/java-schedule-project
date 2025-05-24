package com.doorcs.schedule.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doorcs.schedule.domain.Schedule;
import com.doorcs.schedule.domain.ScheduleRepository;
import com.doorcs.schedule.service.request.CreateScheduleRequest;
import com.doorcs.schedule.service.response.CreateScheduleResponse;
import com.doorcs.schedule.service.response.ReadScheduleResponse;

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

    @Transactional(readOnly = true)
    public List<ReadScheduleResponse> getAll(String name, Date date) {
        return scheduleRepository.findAll(name, date).stream()
            .map(schedule -> new ReadScheduleResponse(
                schedule.getContent(),
                schedule.getName(),
                schedule.getModifiedAt()
            )).toList();
    }

    @Transactional(readOnly = true)
    public ReadScheduleResponse getById(Long id) {
        Schedule schedule = scheduleRepository.findById(id);

        return new ReadScheduleResponse(
            schedule.getContent(),
            schedule.getName(),
            schedule.getModifiedAt()
        );
    }
}
