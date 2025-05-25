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
import com.doorcs.schedule.service.request.UpdateUserRequest;
import com.doorcs.schedule.service.response.CreateUserResponse;
import com.doorcs.schedule.service.response.DeleteUserResponse;
import com.doorcs.schedule.service.response.SigninResponse;
import com.doorcs.schedule.service.response.UpdateUserResponse;

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

    @Transactional
    public UpdateUserResponse update(String jwt, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(jwtUtil.getUserId(jwt));

        if (user == null) {
            throw new BadRequestException("로그인이 필요합니다.");
        }

        user.update(
            updateUserRequest.name() == null ? user.getName() : updateUserRequest.name(),
            updateUserRequest.password() == null ? user.getPassword() : updateUserRequest.password(),
            updateUserRequest.email() == null ? user.getEmail() : updateUserRequest.email()
        );

        int affectedRow = userRepository.update(user);

        if (affectedRow != 1) {
            throw new BadRequestException("사용자 정보 수정에 실패했습니다.");
        }

        return new UpdateUserResponse(
            user.getName(),
            user.getEmail(),
            user.getModifiedAt()
        );
    }

    @Transactional
    public DeleteUserResponse delete(String jwt, String password) {
        Long userId = jwtUtil.getUserId(jwt);
        User user = userRepository.findById(userId);

        if (password == null || password.isEmpty() || !user.getPassword().equals(password)) {
            throw new BadRequestException("비밀번호가 틀립니다.");
        }

        int affectedRow = userRepository.delete(userId);

        if (affectedRow != 1) {
            throw new BadRequestException("사용자 삭제에 실패했습니다.");
        }

        return new DeleteUserResponse(userId);
    }
}
