package com.investmentFunds.investmentFunds.infrastructure.entrypoints.handler;

import com.investmentFunds.investmentFunds.domain.model.common.BusinessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handleGeneral(DuplicateKeyException ex) {
        // Loggear el error real para el desarrollador
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("CONFLICT", "Registro duplicado"));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleGeneral(NoSuchElementException ex) {
        // Loggear el error real para el desarrollador
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ErrorResponse("NO_CONTENT", "No se encuentra el registro"));
    }

}
record ErrorResponse(String code, String message) {}


