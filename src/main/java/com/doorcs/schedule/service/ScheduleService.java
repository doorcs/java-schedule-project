package com.doorcs.schedule.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doorcs.schedule.domain.Schedule;
import com.doorcs.schedule.domain.ScheduleRepository;
import com.doorcs.schedule.domain.User;
import com.doorcs.schedule.domain.UserRepository;
import com.doorcs.schedule.exception.BadRequestException;
import com.doorcs.schedule.service.request.CreateScheduleRequest;
import com.doorcs.schedule.service.request.UpdateScheduleRequest;
import com.doorcs.schedule.service.response.CreateScheduleResponse;
import com.doorcs.schedule.service.response.ReadScheduleResponse;
import com.doorcs.schedule.service.response.UpdateScheduleResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // @Transactional
    // public CreateScheduleResponse create(CreateScheduleRequest createScheduleRequest) {
    //     int affectedRow = scheduleRepository.save(Schedule.of(
    //             // id는 auto increment
    //             createScheduleRequest.content(),
    //             createScheduleRequest.userId(),
    //             createScheduleRequest.date()
    //             // modified_at 필드는 created_at 필드와 같은 값 사용
    //         )
    //     );
    //
    //     if (affectedRow != 1) {
    //         throw new BadRequestException("일정 생성을 실패했습니다.");
    //     }
    //
    //     return new CreateScheduleResponse(
    //         createScheduleRequest.content(),
    //         createScheduleRequest.name(),
    //         createScheduleRequest.date()
    //     );
    // }

    @Transactional(readOnly = true)
    public List<ReadScheduleResponse> getAll(Long userId, Date date) {
        return scheduleRepository.findAll(userId, date).stream()
            .map(schedule -> {
                User user = userRepository.findById(schedule.getUserId());
                String userName = user != null ? user.getName() : "탈퇴한 사용자";
                return new ReadScheduleResponse(
                    schedule.getContent(),
                    userName,
                    schedule.getModifiedAt()
                );
            }).toList();
    }

    @Transactional(readOnly = true)
    public ReadScheduleResponse getById(Long id) {
        Schedule schedule = scheduleRepository.findById(id);
        User user = userRepository.findById(schedule.getUserId());
        String userName = user != null ? user.getName() : "탈퇴한 사용자";
        return new ReadScheduleResponse(
            schedule.getContent(),
            userName,
            schedule.getModifiedAt()
        );
    }

    // @Transactional
    // public UpdateScheduleResponse update(
    //     Long id,
    //     UpdateScheduleRequest updateScheduleRequest
    // ) {
    //     Schedule schedule = scheduleRepository.findById(id);
    //
    //     if (updateScheduleRequest.password() == null ||
    //         updateScheduleRequest.password().isEmpty() ||
    //         !schedule.getPassword().equals(updateScheduleRequest.password())
    //     ) {
    //         throw new BadRequestException("비밀번호가 틀립니다.");
    //     }
    //
    //     if (updateScheduleRequest.content() == null && updateScheduleRequest.name() == null) {
    //         throw new BadRequestException("수정할 내용이 없습니다.");
    //     }
    //
    //     schedule.update(
    //         updateScheduleRequest.content() != null ? updateScheduleRequest.content() : schedule.getContent(),
    //         updateScheduleRequest.name() != null ? updateScheduleRequest.name() : schedule.getName()
    //     );
    //
    //     int affectedRow = scheduleRepository.update(schedule);
    //
    //     if (affectedRow != 1) {
    //         throw new BadRequestException("일정 수정을 실패했습니다.");
    //     }
    //
    //     return new UpdateScheduleResponse(
    //         schedule.getContent(),
    //         schedule.getName(),
    //         schedule.getModifiedAt()
    //     );
    // }
//
//     @Transactional
//     public DeleteScheduleResponse delete(Long id, String password) {
//         Schedule schedule = scheduleRepository.findById(id);
//
//         if (password == null || password.isEmpty() || !schedule.getPassword().equals(password)) {
//             throw new BadRequestException("비밀번호가 틀립니다.");
//         }
//
//         int affectedRow = scheduleRepository.delete(id);
//
//         if (affectedRow != 1) {
//             throw new BadRequestException("일정 삭제를 실패했습니다.");
//         }
//
//         return new DeleteScheduleResponse(id);
//     }
}
