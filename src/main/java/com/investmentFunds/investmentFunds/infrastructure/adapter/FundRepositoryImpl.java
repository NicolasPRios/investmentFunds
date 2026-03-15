package com.investmentFunds.investmentFunds.infrastructure.adapter;

import com.investmentFunds.investmentFunds.domain.model.fund.Fund;
import com.investmentFunds.investmentFunds.domain.model.fund.gateways.FundRepository;
import com.investmentFunds.investmentFunds.infrastructure.mapper.FundMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FundRepositoryImpl implements FundRepository {

    private final FundMongoRepository fundMongoRepository;
    private final FundMapper fundMapper;

    public FundRepositoryImpl(FundMongoRepository fundMongoRepository, FundMapper fundMapper) {
        this.fundMongoRepository = fundMongoRepository;
        this.fundMapper = fundMapper;
    }

    @Override
    public Fund getById(Integer id) {
        return fundMapper.toFund(fundMongoRepository.findById(id).get());
    }

    @Override
    public Fund saveFund(Fund fund) {
        return fundMapper.toFund(fundMongoRepository.save(fundMapper.toFundEntity(fund)));
    }
}
