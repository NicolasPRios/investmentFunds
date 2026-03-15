package com.investmentFunds.investmentFunds.infrastructure.entrypoints;

import com.investmentFunds.investmentFunds.domain.model.fund.Fund;
import com.investmentFunds.investmentFunds.domain.usecase.FundUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/fund", produces = MediaType.APPLICATION_JSON_VALUE)
public class FundsController {

    private final FundUseCase fundUseCase;

    public FundsController(FundUseCase fundUseCase) {
        this.fundUseCase = fundUseCase;
    }

    @PostMapping
    public ResponseEntity<Fund> createFund(@RequestBody Fund fund){
        return new ResponseEntity<>(fundUseCase.saveFund(fund), HttpStatus.CREATED);
    }
}
