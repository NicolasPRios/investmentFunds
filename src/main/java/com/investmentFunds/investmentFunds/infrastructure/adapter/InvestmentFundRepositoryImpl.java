package com.investmentFunds.investmentFunds.infrastructure.adapter;

import com.investmentFunds.investmentFunds.domain.model.investmentfund.InvestmentFund;
import com.investmentFunds.investmentFunds.domain.model.investmentfund.gateways.InvestmentFundRepository;
import com.investmentFunds.investmentFunds.infrastructure.mapper.InvestmentFundMapper;
import org.springframework.stereotype.Repository;

@Repository
public class InvestmentFundRepositoryImpl implements InvestmentFundRepository {

    private final InvestmentFundMongoRepository investmentFundMongoRepository;
    private final InvestmentFundMapper investmentFundMapper;

    public InvestmentFundRepositoryImpl(InvestmentFundMongoRepository investmentFundMongoRepository, InvestmentFundMapper investmentFundMapper) {
        this.investmentFundMongoRepository = investmentFundMongoRepository;
        this.investmentFundMapper = investmentFundMapper;
    }

    @Override
    public InvestmentFund byId(Integer id) {
        return investmentFundMapper.toInvestmentFund(investmentFundMongoRepository.findById(id).get());
    }

    @Override
    public InvestmentFund save(InvestmentFund investmentFund) {
        return investmentFundMapper.toInvestmentFund(investmentFundMongoRepository.save(investmentFundMapper.toInvestmentFundEntity(investmentFund)));
    }

    @Override
    public Iterable<InvestmentFund> allInvestmentFundsByIdUser(Integer idUser) {
        return investmentFundMapper.toInvestmentFunds(investmentFundMongoRepository.findAll());
    }
}
