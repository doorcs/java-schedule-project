package com.doorcs.schedule.service;

import java.sql.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doorcs.schedule.domain.User;
import com.doorcs.schedule.domain.UserRepository;
import com.doorcs.schedule.exception.BadRequestException;
import com.doorcs.schedule.jwt.JwtUtil;
import com.doorcs.schedule.service.request.CreateUserRequest;
import com.doorcs.schedule.service.request.SigninRequest;
import com.doorcs.schedule.service.response.CreateUserResponse;
import com.doorcs.schedule.service.response.SigninResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CreateUserResponse create(CreateUserRequest createUserRequest) {
        Date createdAt = new Date(System.currentTimeMillis());

        int affectedRow = userRepository.save(User.of(
                createUserRequest.name(),
                createUserRequest.password(),
                createUserRequest.email(),
                createdAt // 입력으로 유저 생성 날짜를 받는건 뭔가 이상하다. 이건 시스템에서 관리하는게 맞는 것 같다.
            )
        );

        if (affectedRow != 1) {
            throw new BadRequestException("사용자 생성에 실패했습니다.");
        }

        return new CreateUserResponse(
            createUserRequest.name(),
            createUserRequest.email(),
            createdAt
        );
    }

    @Transactional(readOnly = true)
    public SigninResponse signin(SigninRequest signinRequest) {
        User user = userRepository.findByEmail(signinRequest.email()); // UNIQUE 필드인 email 활용

        if (user == null || !user.getPassword().equals(signinRequest.password())) {
            throw new BadRequestException("로그인에 실패했습니다.");
        }

        return new SigninResponse(
            jwtUtil.createJwt(user.getId())
        );
    }
}
