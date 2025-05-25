package com.doorcs.schedule.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.doorcs.schedule.exception.BadRequestException;
import com.doorcs.schedule.exception.NotFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SignatureException.class)
    public ProblemDetail handleSignatureException(SignatureException e) {
        log.warn("[Signature Exception]", e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "유효하지 않은 서명입니다.");
    }

    @ExceptionHandler(SecurityException.class)
    public ProblemDetail handleSecurityException(SecurityException e) {
        log.warn("[Security Exception]", e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "인증되지 않은 요청입니다.");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ProblemDetail handleMalformedJwtException(MalformedJwtException e) {
        log.warn("[Malformed JWT]", e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "잘못된 JWT 형식입니다.");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail handleExpiredJwtException(ExpiredJwtException e) {
        log.warn("[Expired JWT]", e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "JWT가 만료되었습니다.");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ProblemDetail handleUnsupportedJwtException(UnsupportedJwtException e) {
        log.warn("[Unsupported JWT]", e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "지원하지 않는 JWT입니다.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("[Illegal Argument]", e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
    }

    @ExceptionHandler(JwtException.class)
    public ProblemDetail handleJwtException(JwtException e) {
        log.warn("[JWT Exception]", e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "JWT 처리 중 오류가 발생했습니다.");
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ProblemDetail handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        log.warn("[Empty Result]", e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다.");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ProblemDetail handleNoResourceFoundException(NoResourceFoundException e) {
        log.warn("[No Resource]", e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다.");
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(BadRequestException e) {
        log.warn("[Bad Request]", e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException e) {
        log.warn("[Not Found]", e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다.");
    }

    @ExceptionHandler(DataAccessException.class)
    public ProblemDetail handleDataAccessException(DataAccessException e) {
        log.error("[JDBC Error]", e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 오류가 발생했습니다.");
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e) {

        log.error("[Internal Server Error]", e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
    }
}
