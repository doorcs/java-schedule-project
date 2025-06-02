package com.doorcs.schedule.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doorcs.schedule.domain.Schedule;
import com.doorcs.schedule.domain.ScheduleRepository;
import com.doorcs.schedule.domain.User;
import com.doorcs.schedule.domain.UserRepository;
import com.doorcs.schedule.exception.BadRequestException;
import com.doorcs.schedule.jwt.JwtUtil;
import com.doorcs.schedule.service.request.CreateScheduleRequest;
import com.doorcs.schedule.service.request.UpdateScheduleRequest;
import com.doorcs.schedule.service.response.CreateScheduleResponse;
import com.doorcs.schedule.service.response.DeleteScheduleResponse;
import com.doorcs.schedule.service.response.ReadScheduleResponse;
import com.doorcs.schedule.service.response.UpdateScheduleResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CreateScheduleResponse create(String jwt, CreateScheduleRequest createScheduleRequest) {
        Long userId = jwtUtil.getUserId(jwt);
        Date createdAt = new Date(System.currentTimeMillis());

        int affectedRow = scheduleRepository.save(Schedule.of(
                // id는 auto increment
                userId,
                createScheduleRequest.content(),
                createdAt
                // modified_at 필드는 created_at 필드와 같은 값 사용
            )
        );

        if (affectedRow != 1) {
            throw new BadRequestException("일정 생성을 실패했습니다.");
        }

        return new CreateScheduleResponse(
            userId,
            createScheduleRequest.content(),
            createdAt
        );
    }

    @Transactional(readOnly = true)
    public List<ReadScheduleResponse> getAll(Long userId, Date date, int page, int pageSize) {
        return scheduleRepository.findAllWithUser(userId, date, page, pageSize).stream()
            .map(row -> new ReadScheduleResponse( // 람다식 안에서 유저를 조회하던 코드 제거 (리포지토리에서 Join으로 처리)
                String(row.get("content")),
                String(row.get("user_name")),
                Date(row.get("modified_at"))
            )).toList();
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

    @Transactional
    public UpdateScheduleResponse update(
        String jwt,
        Long id,
        UpdateScheduleRequest updateScheduleRequest
    ) {
        Long userId = jwtUtil.getUserId(jwt);
        Schedule schedule = scheduleRepository.findById(id);

        if (schedule == null) {
            throw new BadRequestException("존재하지 않는 일정입니다.");
        }

        if (!schedule.getUserId().equals(userId)) {
            throw new BadRequestException("로그인이 필요합니다.");
        }

        if (updateScheduleRequest.content() == null) {
            throw new BadRequestException("수정할 내용이 없습니다.");
        }

        schedule.update(
            updateScheduleRequest.content()
        );

        int affectedRow = scheduleRepository.update(schedule);

        if (affectedRow != 1) {
            throw new BadRequestException("일정 수정을 실패했습니다.");
        }

        return new UpdateScheduleResponse(
            schedule.getContent(),
            schedule.getModifiedAt()
        );
    }

    @Transactional
    public DeleteScheduleResponse delete(String jwt, Long id) {
        Long userId = jwtUtil.getUserId(jwt);
        Schedule schedule = scheduleRepository.findById(id);

        if (schedule == null) {
            throw new BadRequestException("존재하지 않는 일정입니다.");
        }

        if (!schedule.getUserId().equals(userId)) {
            throw new BadRequestException("로그인이 필요합니다.");
        }

        int affectedRow = scheduleRepository.delete(id);

        if (affectedRow != 1) {
            throw new BadRequestException("일정 삭제를 실패했습니다.");
        }

        return new DeleteScheduleResponse(id, schedule.getContent());
    }
}
