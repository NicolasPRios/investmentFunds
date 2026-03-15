package com.investmentFunds.investmentFunds.infrastructure.entrypoints;

import com.investmentFunds.investmentFunds.domain.model.investmentfund.InvestmentFund;
import com.investmentFunds.investmentFunds.domain.usecase.InvestmentFundUseCase;
import com.investmentFunds.investmentFunds.infrastructure.entrypoints.DTO.InvestmentFundDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class InvestmentFundController {

    private final InvestmentFundUseCase investmentFundUseCase;


    public InvestmentFundController(InvestmentFundUseCase investmentFundUseCase) {
        this.investmentFundUseCase = investmentFundUseCase;
    }

    @GetMapping
    public ResponseEntity<Iterable<InvestmentFund>> allInvestmentFundsByIdUser(@RequestParam Integer id){
        return ResponseEntity.ok().body(investmentFundUseCase.allInvestmentFundsByIdUser(id));
    }

    @PostMapping
    public ResponseEntity<InvestmentFund> createInvestmentFund(@RequestBody InvestmentFundDTO investmentFundDTO){
            return ResponseEntity.ok().body(investmentFundUseCase.save(investmentFundDTO));
    }

    @PutMapping
    public ResponseEntity<InvestmentFund> CancelSubscription(@RequestParam Integer id){
        return ResponseEntity.ok().body(investmentFundUseCase.cancelSubscription(id));
    }
}
