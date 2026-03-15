package com.investmentFunds.investmentFunds.domain.model.investmentfund.gateways;

import com.investmentFunds.investmentFunds.domain.model.investmentfund.InvestmentFund;

public interface InvestmentFundRepository {
    InvestmentFund byId(Integer id);
    InvestmentFund save(InvestmentFund investmentFund);
    Iterable<InvestmentFund> allInvestmentFundsByIdUser(Integer idUser);
}
