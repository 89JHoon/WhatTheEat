package com.example.whattheeat.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation (DataIntegrityViolationException e){
        log.error("가게 이름 중복 오류. :{}",e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("이미 등록된 가게입니다.");
    }
}
