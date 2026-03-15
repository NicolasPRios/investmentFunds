package com.investmentFunds.investmentFunds.domain.usecase;

import com.investmentFunds.investmentFunds.domain.model.fund.Fund;
import com.investmentFunds.investmentFunds.domain.model.fund.gateways.FundRepository;

public class FundUseCase {
    private final FundRepository fundRepository;

    public FundUseCase(FundRepository fundRepository) {
        this.fundRepository = fundRepository;
    }

    public Fund saveFund (Fund fund){
        return fundRepository.saveFund(fund);
    }
}
