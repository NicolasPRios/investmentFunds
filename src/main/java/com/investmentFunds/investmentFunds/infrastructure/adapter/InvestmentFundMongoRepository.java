package com.investmentFunds.investmentFunds.infrastructure.adapter;

import com.investmentFunds.investmentFunds.infrastructure.entity.InvestmentFundEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvestmentFundMongoRepository extends MongoRepository<InvestmentFundEntity,Integer> {
}
