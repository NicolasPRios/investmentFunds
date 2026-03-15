package com.investmentFunds.investmentFunds.infrastructure.adapter;

import com.investmentFunds.investmentFunds.infrastructure.entity.FundEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FundMongoRepository extends MongoRepository<FundEntity,Integer> {
}
