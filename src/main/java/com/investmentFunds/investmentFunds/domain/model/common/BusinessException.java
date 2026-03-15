package com.investmentFunds.investmentFunds.domain.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private final String code; // Ej: "BALANCE_LOW"

    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }
}
