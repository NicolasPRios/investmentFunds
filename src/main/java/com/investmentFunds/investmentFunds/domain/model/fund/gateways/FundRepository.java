package com.investmentFunds.investmentFunds.domain.model.fund.gateways;

import com.investmentFunds.investmentFunds.domain.model.fund.Fund;

public interface FundRepository {
    Fund getById(Integer id);
    Fund saveFund(Fund fund);
}
